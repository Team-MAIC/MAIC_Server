package com.kurly.projectmaic.domain.das.api;

import com.kurly.projectmaic.domain.das.application.BasketService;
import com.kurly.projectmaic.domain.das.dto.request.BasketInternalRequest;
import com.kurly.projectmaic.domain.das.dto.request.BasketsMappingRequest;
import com.kurly.projectmaic.domain.das.dto.response.BasketInternalResponse;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("/mapping/{centerId}/{passage}")
	public CustomResponseEntity<Void> mapping(
		@PathVariable final long centerId,
		@PathVariable final int passage,
		@RequestBody final BasketsMappingRequest baskets
	) {
		basketService.mapping(centerId, passage, baskets);
		return CustomResponseEntity.success();
	}
}
