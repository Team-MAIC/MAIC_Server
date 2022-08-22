package com.kurly.projectmaic.domain.das.api;

import com.kurly.projectmaic.domain.das.dto.response.BasketColorResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kurly.projectmaic.domain.das.application.BasketService;
import com.kurly.projectmaic.domain.das.dto.request.BasketValidRequest;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/das/todos/baskets")
@RequiredArgsConstructor
public class BasketController {

	private final BasketService basketService;

	@PostMapping("/{dasTodoId}")
	public CustomResponseEntity<BasketColorResponse> completeDasTodo(
		@PathVariable final long dasTodoId,
		@RequestBody final BasketValidRequest request
	) {
		return CustomResponseEntity.success(basketService.completeDasTodo(dasTodoId, request.basketWeight()));
	}
}
