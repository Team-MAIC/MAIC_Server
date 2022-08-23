package com.kurly.projectmaic.domain.das.application;

import com.kurly.projectmaic.domain.das.dao.DasTodoRepository;
import com.kurly.projectmaic.domain.das.dao.RedisBasketRepository;
import com.kurly.projectmaic.domain.das.domain.DasTodo;
import com.kurly.projectmaic.domain.das.dto.request.BasketInternalRequest;
import com.kurly.projectmaic.domain.das.dto.request.BasketsMappingRequest;
import com.kurly.projectmaic.domain.das.dto.response.BasketInfoResponse;
import com.kurly.projectmaic.domain.das.dto.response.BasketInternalResponse;
import com.kurly.projectmaic.domain.das.dto.response.BasketMappingResponse;
import com.kurly.projectmaic.domain.das.dto.response.DasTodoResponse;
import com.kurly.projectmaic.domain.das.enumeration.BasketStatus;
import com.kurly.projectmaic.domain.das.exception.DasTodoAlreadyException;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;
import com.kurly.projectmaic.global.common.response.ResponseCode;
import com.kurly.projectmaic.global.common.utils.RedisChannelUtils;
import com.kurly.projectmaic.global.queue.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasketService {

	private final DasTodoRepository dasTodoRepository;
	private final RedisPublisher publisher;
	private final RedisBasketRepository redisBasketRepository;

	@Transactional
	public BasketInternalResponse refreshDasTodo(final BasketInternalRequest resquest) {
		if (resquest == null) {
			return null;
		}

		double basketDifference = Math.abs((resquest.currentBasketWeight() - resquest.basketWeight()));
		double totalWeight = resquest.productAmount() * resquest.productWeight();
		int num = (int) Math.round(Math.abs(basketDifference) / resquest.productWeight());
		BasketStatus status = resquest.status();

		if (((totalWeight * 0.9) <= basketDifference && resquest.status() != BasketStatus.READY) &&
			((totalWeight * 1.1) >= basketDifference && resquest.status() != BasketStatus.READY)) {
			dasTodoRepository.updateStatus(resquest.dasTodoId(), BasketStatus.READY);

			status = BasketStatus.READY;
		}

		if (((totalWeight * 0.9) > basketDifference && resquest.status() != BasketStatus.WRONG) ||
			((totalWeight * 1.1) < basketDifference && resquest.status() != BasketStatus.WRONG)) {
			dasTodoRepository.updateStatus(resquest.dasTodoId(), BasketStatus.WRONG);

			status = BasketStatus.WRONG;
		}

		BasketMappingResponse basketMappingResponse = new BasketMappingResponse(
			redisBasketRepository.getKey(resquest.centerId(),resquest.passage(), resquest.basketNum()),
			resquest.basketNum()
		);

		publisher.publish(
			RedisChannelUtils.getDasTodoTopic(resquest.centerId(), resquest.passage()),
			CustomResponseEntity.success(
				new BasketInfoResponse(
					basketMappingResponse,
					new DasTodoResponse(
						resquest.roundId(),
						resquest.productId(),
						resquest.productName(),
						resquest.productAmount(),
						num,
						resquest.color(),
						status
					)
				)
			));

		return new BasketInternalResponse(
			resquest.dasTodoId(),
			resquest.centerId(),
			resquest.passage(),
			resquest.roundId(),
			resquest.productId(),
			resquest.productName(),
			resquest.basketWeight(),
			resquest.productAmount(),
			resquest.productWeight(),
			status,
			resquest.color()
		);
	}

	@Transactional
	public BasketInternalResponse completeDasTodo(final BasketInternalRequest resquest) {
		if (resquest == null) {
			return null;
		}

		if (resquest.status() == BasketStatus.FINISH) {
			throw new DasTodoAlreadyException(ResponseCode.ALREADY_DAS_TODO, String.format("dasTodoId : {}", resquest.dasTodoId()));
		}

		double basketDifference = Math.abs((resquest.currentBasketWeight() - resquest.basketWeight()));
		double totalWeight = resquest.productAmount() * resquest.productWeight();

		if (((totalWeight * 0.9) > basketDifference && resquest.status() != BasketStatus.WRONG) ||
			((totalWeight * 1.1) < basketDifference && resquest.status() != BasketStatus.WRONG)) {

			int num = (int) Math.round(Math.abs(basketDifference) / resquest.productWeight());

			dasTodoRepository.updateStatus(resquest.dasTodoId(), BasketStatus.WRONG);

			pubDasTodoMessage(resquest, num);

			return new BasketInternalResponse(
				resquest.dasTodoId(),
				resquest.centerId(),
				resquest.passage(),
				resquest.roundId(),
				resquest.productId(),
				resquest.productName(),
				resquest.basketWeight(),
				resquest.productAmount(),
				resquest.productWeight(),
				BasketStatus.WRONG,
				resquest.color()
			);
		}

		dasTodoRepository.updateStatus(resquest.dasTodoId(), BasketStatus.FINISH);
		dasTodoRepository.completeStatus(resquest.dasTodoId());
		dasTodoRepository.updateWeight(resquest.centerId(), resquest.roundId(), resquest.passage(),
			resquest.basketNum(), resquest.currentBasketWeight());

		DasTodo nextDasTodo = dasTodoRepository.nextDasTodo(resquest.centerId(), resquest.passage(),
			resquest.roundId(), resquest.basketNum());

		if (nextDasTodo == null) {
			BasketMappingResponse basketMappingResponse = new BasketMappingResponse(
				redisBasketRepository.getKey(resquest.centerId(),resquest.passage(), resquest.basketNum()),
				resquest.basketNum()
			);

			publisher.publish(
				RedisChannelUtils.getDasTodoTopic(resquest.centerId(), resquest.passage()),
				CustomResponseEntity.success(
					new BasketInfoResponse(
						basketMappingResponse,
						null
					)
				));

			return null;
		}

		BasketMappingResponse basketMappingResponse = new BasketMappingResponse(
			redisBasketRepository.getKey(nextDasTodo.getCenterId(), nextDasTodo.getPassage(), nextDasTodo.getBasketNum()),
			resquest.basketNum()
		);

		publisher.publish(
			RedisChannelUtils.getDasTodoTopic(nextDasTodo.getCenterId(), nextDasTodo.getPassage()),
			CustomResponseEntity.success(
				new BasketInfoResponse(
					basketMappingResponse,
					new DasTodoResponse(
						nextDasTodo.getRoundId(),
						nextDasTodo.getProductId(),
						nextDasTodo.getProductName(),
						nextDasTodo.getProductAmount(),
						0,
						nextDasTodo.getBasketColor(),
						nextDasTodo.getStatus()
					)
				)
			));

		return new BasketInternalResponse(
			nextDasTodo.getDasTodoId(),
			nextDasTodo.getCenterId(),
			nextDasTodo.getPassage(),
			nextDasTodo.getRoundId(),
			nextDasTodo.getProductId(),
			nextDasTodo.getProductName(),
			nextDasTodo.getBasketWeight(),
			nextDasTodo.getProductAmount(),
			nextDasTodo.getProductWeight(),
			nextDasTodo.getStatus(),
			nextDasTodo.getBasketColor());
	}

	private void pubDasTodoMessage(final BasketInternalRequest resquest, final int num) {
		BasketMappingResponse basketMappingResponse = new BasketMappingResponse(
			redisBasketRepository.getKey(resquest.centerId(), resquest.passage(), resquest.basketNum()),
			resquest.basketNum()
		);

		publisher.publish(
			RedisChannelUtils.getDasTodoTopic(resquest.centerId(), resquest.passage()),
			CustomResponseEntity.success(
				new BasketInfoResponse(
					basketMappingResponse,
					new DasTodoResponse(
						resquest.roundId(),
						resquest.productId(),
						resquest.productName(),
						resquest.productAmount(),
						num,
						resquest.color(),
						BasketStatus.WRONG
					)
				)
			));
	}

	public void mapping(final long centerId, final int passage, final BasketsMappingRequest baskets) {
		redisBasketRepository.deleteKeys(centerId, passage);
		redisBasketRepository.setKeys(centerId, passage, baskets.baskets());
	}
}
