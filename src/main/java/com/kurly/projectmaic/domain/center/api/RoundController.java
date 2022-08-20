package com.kurly.projectmaic.domain.center.api;

import static com.kurly.projectmaic.global.common.constant.WorkerIdHeader.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kurly.projectmaic.domain.center.application.RoundService;
import com.kurly.projectmaic.domain.center.dto.response.RoundsResponse;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rounds")
@RequiredArgsConstructor
public class RoundController {

	private final RoundService roundService;

	@GetMapping
	public CustomResponseEntity<RoundsResponse> getRounds(
		@RequestHeader(WORKER_ID) final long workerId
	) {
		return CustomResponseEntity.success(roundService.getRounds(workerId));
	}
}
