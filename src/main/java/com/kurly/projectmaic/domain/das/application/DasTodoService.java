package com.kurly.projectmaic.domain.das.application;

import static com.kurly.projectmaic.global.common.constant.PIckDasCount.PICK_DAS_COUNT;
import static com.kurly.projectmaic.global.common.response.ResponseCode.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.kurly.projectmaic.domain.das.dao.RedisBasketRepository;
import com.kurly.projectmaic.domain.das.dto.querydsl.ProductsColorDto;
import com.kurly.projectmaic.domain.das.dto.response.*;
import com.kurly.projectmaic.domain.model.StatusType;
import com.kurly.projectmaic.domain.pick.dao.PickTodoRepository;
import com.kurly.projectmaic.domain.pick.domain.PickTodo;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kurly.projectmaic.domain.center.dao.RoundRepository;
import com.kurly.projectmaic.domain.center.domain.Round;
import com.kurly.projectmaic.domain.center.enumeration.RoundStatus;
import com.kurly.projectmaic.domain.center.exception.RoundNotFoundException;
import com.kurly.projectmaic.domain.das.dao.DasBasketReopository;
import com.kurly.projectmaic.domain.das.dao.DasTodoRepository;
import com.kurly.projectmaic.domain.das.domain.DasBasket;
import com.kurly.projectmaic.domain.das.domain.DasTodo;
import com.kurly.projectmaic.domain.das.enumeration.BasketColor;
import com.kurly.projectmaic.domain.das.enumeration.BasketStatus;
import com.kurly.projectmaic.domain.das.exception.EveryBasketColorsUsedException;
import com.kurly.projectmaic.domain.order.dao.OrderInfoRepository;
import com.kurly.projectmaic.domain.order.dao.OrderProductRepository;
import com.kurly.projectmaic.domain.order.dto.querydsl.OrderInfoIdByRoundDto;
import com.kurly.projectmaic.domain.order.dto.querydsl.OrderProductDto;
import com.kurly.projectmaic.domain.das.exception.DasNotFoundException;
import com.kurly.projectmaic.domain.product.dao.ProductRepository;
import com.kurly.projectmaic.domain.product.dto.ValidProductsDto;
import com.kurly.projectmaic.global.common.constant.RedisTopic;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;
import com.kurly.projectmaic.global.common.response.ResponseCode;
import com.kurly.projectmaic.global.common.utils.RedisChannelUtils;
import com.kurly.projectmaic.global.queue.RedisPublisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DasTodoService {

	private final DasTodoRepository dasTodoRepository;
	private final DasBasketReopository dasBasketReopository;
	private final OrderProductRepository orderProductRepository;
	private final OrderInfoRepository orderInfoRepository;
	private final RoundRepository roundRepository;
	private final ProductRepository productRepository;
	private final RedisPublisher publisher;
	private final RedisBasketRepository redisBasketRepository;
	private final PickTodoRepository pickTodoRepository;

	@Transactional
	public DasTodoSummaryResponse refreshDasTodos(final long centerID, final int passage) {
		Round round = getRound(centerID, passage);

		List<Long> orderIds = getOrderIds(round.getRoundId());

		List<DasBasket> dasBaskets = dasBasketReopository.findAllByOrderInfoId(orderIds);

		if (dasBaskets.size() == 0) {
			dasBasketReopository.bulkSave(orderIds);
			dasBaskets = dasBasketReopository.findAllByOrderInfoId(orderIds);
		}

		Map<Long, Integer> baskets = dasBaskets.stream()
			.collect(Collectors.toMap(DasBasket::getOrderInfoId, DasBasket::getBasketNum));

		if (round.getPassage() == null) {
			List<OrderProductDto> orderProductDtos = orderProductRepository.getOrderProducts(round.getRoundId());
			List<Long> productIds = orderProductDtos.stream()
				.map(OrderProductDto::productId)
				.toList();


			ValidProductsDto validProductsDto = productRepository.getValidProductCount(productIds);
			dasTodoRepository.bulkSave(centerID, passage, round.getRoundId(), orderProductDtos, validProductsDto.products(), baskets);

			round.startDas(passage);
			roundRepository.save(round);
		}

		List<BasketResponse> basketResponses = dasBaskets.stream()
			.map(dasBasket -> new BasketResponse(dasBasket.getBasketNum(),
				dasBasket.getOrderInfoId())
			)
			.toList();

		return new DasTodoSummaryResponse(round.getRoundId(), round.getCenterRoundNumber(), basketResponses);
	}

	private Round getRound(final long centerId, final int passage) {
		Round round = roundRepository.getLastRoundByPassage(centerId, passage)
			.orElse(null);

		if (round != null) {
			return round;
		}

		round = roundRepository.getUnassignedRound(centerId);

		if (round == null) {
			throw new DasNotFoundException(ResponseCode.NOT_FOUND_DAS, "");
		}

		return round;
	}

	private List<Long> getOrderIds(final long roundId) {
		List<OrderInfoIdByRoundDto> orderInfoIdByRoundDtos = orderInfoRepository.getOrderIdsByRound(roundId);

		return orderInfoIdByRoundDtos.stream()
			.map(OrderInfoIdByRoundDto::orderId)
			.toList();
	}

	@Transactional
	public BasketsInfoResponse getDasTodos(final long roundId,
		final BasketStatus status,
		final BasketColor color) {
		Round round = roundRepository.findById(roundId)
			.orElseThrow(() ->
				new RoundNotFoundException(NOT_FOUND_ROUND, String.format("roundId : {}", roundId)));

		var todos = dasTodoRepository.getDasTodos(roundId, status, color);

		Map<Integer, DasTodo> map = new HashMap<>();

		todos.forEach(t -> {
			if (!map.containsKey(t.getBasketNum())) {
				map.put(t.getBasketNum(), t);
			}
		});

		List<BasketInfoResponse> basketInfoResponses = new ArrayList<>(todos.size());

		for (int i = 0; i < PICK_DAS_COUNT; i++) {
			DasTodo todo = map.get(i);
			DasTodoResponse dasTodoResponse = null;
			BasketInternalResponse basketColorResponse = null;

			if (todo != null) {
				dasTodoResponse = new DasTodoResponse(
					roundId,
					todo.getProductId(),
					todo.getProductName(),
					todo.getProductAmount(),
					0,
					todo.getBasketColor(),
					todo.getStatus()
				);

				basketColorResponse = new BasketInternalResponse(
					todo.getDasTodoId(),
					todo.getCenterId(),
					round.getPassage(),
					todo.getRoundId(),
					todo.getProductId(),
					todo.getProductName(),
					todo.getBasketWeight(),
					todo.getProductAmount(),
					todo.getProductWeight(),
					todo.getStatus(),
					todo.getBasketColor()
				);
			}

			publisher.publish(
				RedisChannelUtils.getDasBasketTopic(round.getCenterId(), round.getPassage(), i),
				CustomResponseEntity.success(basketColorResponse)
			);

			BasketMappingResponse basketMappingResponse = new BasketMappingResponse(
				redisBasketRepository.getKey(round.getCenterId(), round.getPassage(), i),
				i
			);

			basketInfoResponses.add(
				new BasketInfoResponse(
					basketMappingResponse,
					dasTodoResponse
				)
			);
		}

		List<ProductsColorDto> colors = dasTodoRepository.getUsedColor(roundId);

		List<ProductsColorResponse> productsColorResponses = colors.stream()
			.map(c -> new ProductsColorResponse(c.color(), c.productName()))
			.toList();

		return new BasketsInfoResponse(productsColorResponses, basketInfoResponses);
	}

	@Transactional
	public void updateColor(long roundId, long productId) {
		Round round = roundRepository.findById(roundId)
			.orElseThrow(() ->
				new RoundNotFoundException(NOT_FOUND_ROUND, String.format("roundId : {}", roundId)));

		PickTodo pickTodo = pickTodoRepository.getPickTodo(roundId, productId);

		if (pickTodo.getStatus() != StatusType.FINISH) {
			throw new DasNotFoundException(ResponseCode.NOT_DONE_PICK_TODO,
				String.format("pickTodoId : {}", pickTodo.getPickTodoId()));
		}

		List<ProductsColorDto> colors = dasTodoRepository.getUsedColor(roundId);

		List<BasketColor> basketColors = colors.stream()
			.map(ProductsColorDto::color)
			.toList();

		if (colors.size() == 4) {
			throw new EveryBasketColorsUsedException(USED_EVERY_COLORS, "");
		}

		BasketColor color = Arrays.stream(BasketColor.values())
			.filter(c -> !basketColors.contains(c))
			.filter(c -> c != BasketColor.ALL &&
				c != BasketColor.BLACK)
			.findFirst()
			.orElseThrow(() -> new EveryBasketColorsUsedException(USED_EVERY_COLORS, ""));

		var todos = dasTodoRepository.getDasTodos(roundId, BasketStatus.ALL, BasketColor.ALL);

		Map<Integer, DasTodo> map = new HashMap<>();

		todos.forEach(t -> {
			if (!map.containsKey(t.getBasketNum())) {
				map.put(t.getBasketNum(), t);
			}
		});

		for (int i = 0; i < PICK_DAS_COUNT; i++) {
			DasTodo todo = map.get(i);
			BasketInternalResponse basketColorResponse = null;

			if (todo != null) {
				basketColorResponse = new BasketInternalResponse(
					todo.getDasTodoId(),
					todo.getCenterId(),
					round.getPassage(),
					todo.getRoundId(),
					todo.getProductId(),
					todo.getProductName(),
					todo.getBasketWeight(),
					todo.getProductAmount(),
					todo.getProductWeight(),
					todo.getStatus(),
					todo.getBasketColor()
				);
			}

			publisher.publish(
				RedisChannelUtils.getDasBasketTopic(round.getCenterId(), round.getPassage(), i),
				CustomResponseEntity.basketInit(basketColorResponse)
			);
		}

		dasTodoRepository.updateColor(roundId, productId, color);
	}

	public void subscribeSubTodo(final long centerId, final int passage) {

		publisher.publish(
			new ChannelTopic(RedisTopic.SUB),
			CustomResponseEntity.connect(
				RedisChannelUtils.getDasTodoTopicName(centerId, passage))
		);

		for (int i = 0; i < PICK_DAS_COUNT; i++) {
			publisher.publish(
				new ChannelTopic(RedisTopic.SUB),
				CustomResponseEntity.connect(
					RedisChannelUtils.getDasTodoTopicName(centerId, passage) + "/" + i)
			);
		}
	}
}
