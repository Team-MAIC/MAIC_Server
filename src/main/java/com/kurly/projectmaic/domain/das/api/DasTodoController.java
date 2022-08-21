package com.kurly.projectmaic.domain.das.api;

import static com.kurly.projectmaic.global.common.constant.WorkerIdHeader.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kurly.projectmaic.domain.das.application.DasTodoService;
import com.kurly.projectmaic.domain.das.dto.response.DasTodoResponse;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/das/todos")
@RequiredArgsConstructor
public class DasTodoController {

	private final DasTodoService dasTodoService;

	@GetMapping
	public CustomResponseEntity<DasTodoResponse> getCurrentDasInfo(
		@RequestHeader(WORKER_ID) final long workerId,
		@RequestParam final long centerId) {
		return CustomResponseEntity.success(dasTodoService.getDasRounds(workerId, centerId));
	}
}
