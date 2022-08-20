package com.kurly.projectmaic.domain.pick.api;

import static com.kurly.projectmaic.global.common.constant.WorkerIdHeader.*;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kurly.projectmaic.domain.pick.application.PickTodoService;
import com.kurly.projectmaic.domain.pick.dto.request.PickTodoStatusUpdateRequest;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pick/todos")
@RequiredArgsConstructor
public class PickTodoController {

	private final PickTodoService pickTodoService;

	@PutMapping("/{pickTodoId}")
	public CustomResponseEntity<Void> finishPickTodo(
		@RequestHeader(WORKER_ID) final long workerId,
		@PathVariable final long pickTodoId
	) {
		pickTodoService.finishPickTodo(workerId, pickTodoId);
		return CustomResponseEntity.success();
	}
}
