package com.kurly.projectmaic.domain.das.api;

import static com.kurly.projectmaic.global.common.constant.SocketDestination.*;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.kurly.projectmaic.domain.das.application.DasTodoService;
import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.kurly.projectmaic.domain.pick.application.PickTodoService;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodosResponse;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SocketDasTodoController {

	private final SimpMessagingTemplate template;
	private final DasTodoService dasTodoService;

	@MessageMapping("/das/todos/{roundId}")
	public void getDasTodos(@Header(name = "worker-id") final String workerId,
		@DestinationVariable final long roundId) {
		//
		// DasTodosResponse response = dasTodoService.getDasTodos(roundId);
		// dasTodoService.subscribePickChannel(roundId, area);
		//
		// template.convertAndSendToUser(String.valueOf(workerId),
		// 	String.format(PICK_WORKER_DESTINATION_FORMAT, roundId, area),
		// 	CustomResponseEntity.success(response)
		// );
	}
}
