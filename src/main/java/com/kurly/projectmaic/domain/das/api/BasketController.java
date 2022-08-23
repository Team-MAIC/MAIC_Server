package com.kurly.projectmaic.domain.das.api;

import com.kurly.projectmaic.domain.das.application.BasketService;
import com.kurly.projectmaic.domain.das.dto.request.BasketInternalRequest;
import com.kurly.projectmaic.domain.das.dto.response.BasketInternalResponse;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/das/todos/baskets")
@RequiredArgsConstructor
public class BasketController {

	private final BasketService basketService;

	@PostMapping
	public CustomResponseEntity<BasketInternalResponse> completeDasTodo(
		@RequestBody final BasketInternalRequest request
	) {
		return CustomResponseEntity.success(basketService.completeDasTodo(request));
	}

	@PostMapping("/refresh")
	public CustomResponseEntity<BasketInternalResponse> refreshDasTodo(
		@RequestBody(required = false) BasketInternalRequest request
	) {
		return CustomResponseEntity.success(basketService.refreshDasTodo(request));
	}
}
