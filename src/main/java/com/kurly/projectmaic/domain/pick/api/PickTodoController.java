package com.kurly.projectmaic.domain.pick.api;

import static com.kurly.projectmaic.global.common.constant.WorkerIdHeader.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.kurly.projectmaic.domain.pick.application.PickTodoService;
import com.kurly.projectmaic.domain.pick.dto.request.PickTodoStatusUpdateRequest;
import com.kurly.projectmaic.domain.pick.dto.request.PickTodoSubscribeRequest;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodosResponse;
import com.kurly.projectmaic.domain.pick.exception.PickTodoFilterType;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pick/todos")
@RequiredArgsConstructor
public class PickTodoController {

	private final PickTodoService pickTodoService;

	@GetMapping
	public CustomResponseEntity<PickTodosResponse> getPickTodos(
		@RequestHeader(WORKER_ID) final long workerId,
		@RequestParam final long roundId,
		@RequestParam final CenterProductArea area,
		@RequestParam(defaultValue = "ALL") final PickTodoFilterType filterType
	) {
		PickTodosResponse response = pickTodoService.getPickTodos(roundId, area, workerId, filterType);

		return CustomResponseEntity.success(response);
	}

	@PostMapping("/subscribe")
	public CustomResponseEntity<PickTodosResponse> subscribePickTodo(
		@RequestBody final PickTodoSubscribeRequest request
	) {
		pickTodoService.subscribePickChannel(request.roundId(), request.area());

		return CustomResponseEntity.success();
	}

	@PutMapping("/{pickTodoId}")
	public CustomResponseEntity<Void> finishPickTodo(
		@RequestHeader(WORKER_ID) final long workerId,
		@PathVariable final long pickTodoId
	) {
		pickTodoService.finishPickTodo(workerId, pickTodoId);
		return CustomResponseEntity.success();
	}
}
