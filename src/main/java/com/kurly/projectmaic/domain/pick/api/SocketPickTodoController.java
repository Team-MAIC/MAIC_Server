package com.kurly.projectmaic.domain.pick.api;

import static com.kurly.projectmaic.global.common.constant.SocketDestination.*;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.kurly.projectmaic.domain.pick.application.PickTodoService;
import com.kurly.projectmaic.domain.pick.dto.request.PickTodosRequest;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodosResponse;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SocketPickTodoController {

	private final SimpMessagingTemplate template;
	private final PickTodoService pickTodoService;

	@MessageMapping("/pick/todos/{roundId}/{area}")
	public void getPickTodos(@Header(name = "worker-id") final String workerId,
		@DestinationVariable final long roundId,
		@DestinationVariable final CenterProductArea area) {

		PickTodosResponse response = pickTodoService.getPickTodos(roundId, area);
		pickTodoService.subscribePickChannel(roundId, area);

		template.convertAndSendToUser(String.valueOf(workerId),
			String.format(PICK_WORKER_DESTINATION_FORMAT, roundId, area),
			CustomResponseEntity.success(response)
		);
	}

	// @MessageMapping(value = "/pick/todos/{roundId}/{area}")
	// public void getPickTodos(PickTodosRequest request) {
	// 	PickTodosResponse response = pickTodoService.getPickTodos(request.roundId(), request.area());
	//
	// 	template.convertAndSend(
	// 		String.format(PICK_DESTINATION_FORMAT, request.roundId(), request.area()),
	// 		CustomResponseEntity.success(response));
	// }
}
