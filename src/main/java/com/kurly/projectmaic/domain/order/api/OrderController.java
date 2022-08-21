package com.kurly.projectmaic.domain.order.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kurly.projectmaic.domain.order.application.OrderInfoService;
import com.kurly.projectmaic.domain.order.dto.PurchaseRequest;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderInfoService orderInfoService;

	@PostMapping
	public CustomResponseEntity<Void> purchase(@RequestBody final PurchaseRequest request) {
		orderInfoService.purchaseProducts(request);

		return CustomResponseEntity.success();
	}
}
