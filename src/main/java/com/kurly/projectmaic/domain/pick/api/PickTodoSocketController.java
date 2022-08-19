package com.kurly.projectmaic.domain.pick.api;

import static com.kurly.projectmaic.global.common.constant.SocketDestination.*;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.kurly.projectmaic.domain.pick.application.PickTodoService;
import com.kurly.projectmaic.domain.pick.dto.request.PickTodosRequest;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodosResponse;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PickTodoSocketController {

	private final SimpMessagingTemplate template;
	private final PickTodoService pickTodoService;

	@MessageMapping(value = "/pick/todos")
	public void getPickTodos(PickTodosRequest request) {
		PickTodosResponse response = pickTodoService.getPickTodos(request.roundId(), request.area());

		template.convertAndSend(
			String.format(PICK_DESTINATION_FORMAT, request.roundId(), request.area()),
			CustomResponseEntity.success(response));
	}
}
