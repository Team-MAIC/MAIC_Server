package com.kurly.projectmaic.domain.das.api;

import com.kurly.projectmaic.domain.das.application.DasTodoService;
import com.kurly.projectmaic.domain.das.domain.DasTodo;
import com.kurly.projectmaic.domain.das.dto.request.BasketsMappingRequest;
import com.kurly.projectmaic.domain.das.dto.request.DasTodoSubscribeRequest;
import com.kurly.projectmaic.domain.das.dto.response.BasketsInfoResponse;
import com.kurly.projectmaic.domain.das.dto.response.DasTodoSummaryResponse;
import com.kurly.projectmaic.domain.das.enumeration.BasketColor;
import com.kurly.projectmaic.domain.das.enumeration.BasketStatus;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/das/todos")
@RequiredArgsConstructor
public class DasTodoController {

	private final DasTodoService dasTodoService;

	@GetMapping("/refresh")
	public CustomResponseEntity<DasTodoSummaryResponse> getCurrentDasInfo(
		@RequestParam final long centerId,
		@RequestParam final int passage) {
		return CustomResponseEntity.success(dasTodoService.refreshDasTodos(centerId, passage));
	}

	@PostMapping("/subscribe")
	public CustomResponseEntity<Void> subscribeDasTodo(
		@RequestBody final DasTodoSubscribeRequest request
	) {
		dasTodoService.subscribeSubTodo(request.centerId(), request.passage());

		return CustomResponseEntity.success();
	}

	@GetMapping("/{roundId}")
	public CustomResponseEntity<BasketsInfoResponse> getDasTodos(
		@PathVariable final long roundId,
		@RequestParam(defaultValue = "ALL") final BasketStatus status,
		@RequestParam(defaultValue = "ALL") final BasketColor color
	) {
		return CustomResponseEntity.success(dasTodoService.getDasTodos(roundId, status, color));
	}

	@PutMapping("/{roundId}/products/{productId}")
	public CustomResponseEntity<Void> updateColor(
		@PathVariable final long roundId,
		@PathVariable final long productId
	) {
		dasTodoService.updateColor(roundId, productId);
		return CustomResponseEntity.success();
	}

	@GetMapping("/info/{todoId}")
	public CustomResponseEntity<DasTodo> getDasTodoInfo(
		@PathVariable final long todoId
	) {
		return CustomResponseEntity.success(dasTodoService.getDasTodoInfo(todoId));
	}
}
