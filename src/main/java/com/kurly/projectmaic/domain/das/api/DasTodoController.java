package com.kurly.projectmaic.domain.das.api;

import static com.kurly.projectmaic.global.common.constant.WorkerIdHeader.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kurly.projectmaic.domain.das.application.DasTodoService;
import com.kurly.projectmaic.domain.das.dto.response.BasketsInfoResponse;
import com.kurly.projectmaic.domain.das.dto.response.DasTodoSummaryResponse;
import com.kurly.projectmaic.domain.das.enumeration.BasketColor;
import com.kurly.projectmaic.domain.das.enumeration.BasketStatus;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/das/todos")
@RequiredArgsConstructor
public class DasTodoController {

	private final DasTodoService dasTodoService;

	@GetMapping("/refresh")
	public CustomResponseEntity<DasTodoSummaryResponse> getCurrentDasInfo(
		@RequestHeader(WORKER_ID) final long workerId,
		@RequestParam final long centerId) {
		return CustomResponseEntity.success(dasTodoService.getDasRounds(workerId, centerId));
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
	public CustomResponseEntity<?> updateColor(
		@PathVariable final long roundId,
		@PathVariable final long productId
	) {
		dasTodoService.updateColor(roundId, productId);
		return CustomResponseEntity.success();
	}
}
