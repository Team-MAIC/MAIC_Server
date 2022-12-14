package com.kurly.projectmaic.domain.order.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kurly.projectmaic.domain.center.dao.CenterProductRepository;
import com.kurly.projectmaic.domain.center.dao.RoundRepository;
import com.kurly.projectmaic.domain.center.domain.Round;
import com.kurly.projectmaic.domain.center.dto.querydsl.CenterProductDto;
import com.kurly.projectmaic.domain.center.dto.querydsl.CurrentRoundDto;
import com.kurly.projectmaic.domain.center.enumeration.RoundStatus;
import com.kurly.projectmaic.domain.order.dao.OrderInfoRepository;
import com.kurly.projectmaic.domain.order.dao.OrderProductRepository;
import com.kurly.projectmaic.domain.order.domain.OrderInfo;
import com.kurly.projectmaic.domain.order.domain.OrderProduct;
import com.kurly.projectmaic.domain.order.dto.ProductRequest;
import com.kurly.projectmaic.domain.order.dto.PurchaseRequest;
import com.kurly.projectmaic.domain.order.dto.querydsl.OrderProductByRoundIdDto;
import com.kurly.projectmaic.domain.order.exception.PurchaseException;
import com.kurly.projectmaic.domain.pick.dao.PickTodoRepository;
import com.kurly.projectmaic.domain.product.dao.ProductRepository;
import com.kurly.projectmaic.domain.product.dto.ValidProductsDto;
import com.kurly.projectmaic.domain.product.dto.querydsl.ProductDto;
import com.kurly.projectmaic.domain.product.exception.ProductNotFoundException;
import com.kurly.projectmaic.global.common.response.ResponseCode;

import lombok.RequiredArgsConstructor;

import static com.kurly.projectmaic.global.common.constant.PIckDasCount.PICK_DAS_COUNT;

@Service
@RequiredArgsConstructor
public class OrderInfoService {

	private final OrderInfoRepository orderInfoRepository;
	private final OrderProductRepository orderProductRepository;
	private final ProductRepository productRepository;
	private final RoundRepository roundRepository;
	private final CenterProductRepository centerProductRepository;
	private final PickTodoRepository pickTodoRepository;

	@Transactional
	public void purchaseProducts(PurchaseRequest request) {

		List<Long> productIds = request.products().stream()
			.map(ProductRequest::productId)
			.toList();

		ValidProductsDto validProducts = validProducts(request, productIds);
		List<CenterProductDto> centerProductDtos = centerProductRepository.getCenterProducts(request.centerId(), productIds);

		if (productIds.size() != validProducts.count() || productIds.size() != centerProductDtos.size()) {
			throw new PurchaseException(ResponseCode.FAIL_PURCHASE,	"");
		}

		CurrentRoundDto currentRoundDto = getCurrentRoundDto(request);

		OrderInfo orderInfo = new OrderInfo(
			null,
			currentRoundDto.roundId(),
			request.consumerId(),
			validProducts.totalPrice()
		);

		orderInfoRepository.save(orderInfo);

		List<OrderProduct> products = request.products().stream()
			.map(product -> new OrderProduct(
				null,
				orderInfo.getOrderInfoId(),
				product.productId(),
				product.amount()
			))
			.toList();

		orderProductRepository.bulkSave(products);

		checkCompletedRound(request, currentRoundDto);
	}

	private void checkCompletedRound(PurchaseRequest request, CurrentRoundDto currentRoundDto) {
		long roundOrdersCount = orderInfoRepository.countByRoundId(currentRoundDto.roundId());

		if (roundOrdersCount == PICK_DAS_COUNT) {
			roundRepository.updateRoundStatus(currentRoundDto.roundId(), RoundStatus.READY);

			Round round = new Round(
				null,
				request.centerId(),
				currentRoundDto.centerRoundNumber() + 1,
				RoundStatus.WAIT);

			roundRepository.save(round);

			List<OrderProductByRoundIdDto> orderProducts = orderProductRepository.getOrderProductsByRoundId(currentRoundDto.roundId());

			List<Long> productIds = orderProducts.stream()
				.map(OrderProductByRoundIdDto::productId)
				.toList();

			List<ProductDto> productDtos = productRepository.getValidProductCount(productIds).products();
			List<CenterProductDto> centerProductDtos = centerProductRepository.getCenterProducts(request.centerId(), productIds);

			if (orderProducts.size() != productDtos.size() || orderProducts.size() != centerProductDtos.size()) {
				throw new PurchaseException(ResponseCode.FAIL_PURCHASE,	"");
			}

			pickTodoRepository.bulkSave(currentRoundDto.roundId(), orderProducts, productDtos, centerProductDtos);
		}
	}

	private CurrentRoundDto getCurrentRoundDto(PurchaseRequest request) {
		CurrentRoundDto currentRoundDto = roundRepository.getCurrentRoundId(request.centerId());

		if (currentRoundDto == null) {
			Round round = new Round(
				null,
				request.centerId(),
				1L,
				RoundStatus.WAIT);

			roundRepository.save(round);

			currentRoundDto = new CurrentRoundDto(round.getRoundId(), 1L);
		}
		return currentRoundDto;
	}

	private ValidProductsDto validProducts(PurchaseRequest request, List<Long> productIds) {
		ValidProductsDto validProducts = productRepository.getValidProductCount(productIds);

		if (request.products().size() != validProducts.count()) {
			throw new ProductNotFoundException(ResponseCode.NOT_FOUND_PRODUCT,
				String.format("productIds : %s", request.products()));
		}

		return validProducts;
	}
}
