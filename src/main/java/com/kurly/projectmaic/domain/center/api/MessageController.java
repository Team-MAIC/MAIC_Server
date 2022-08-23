package com.kurly.projectmaic.domain.center.api;

import com.kurly.projectmaic.domain.center.application.MessageService;
import com.kurly.projectmaic.domain.center.dto.request.MessageSendRequest;
import com.kurly.projectmaic.domain.center.dto.response.MessagesResponse;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;
import com.kurly.projectmaic.global.external.fcm.response.FcmResonse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.kurly.projectmaic.global.common.constant.WorkerIdHeader.WORKER_ID;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;

	@GetMapping
	public CustomResponseEntity<MessagesResponse> getMessages(
		@RequestHeader(WORKER_ID) final long workerId
	) {
		return CustomResponseEntity.success(messageService.getMessages(workerId));
	}

	@PostMapping
	public CustomResponseEntity<FcmResonse> sendMessage(
		@RequestHeader(WORKER_ID) final long workerId,
		@RequestBody final MessageSendRequest request
	) {
		return CustomResponseEntity.success(messageService.sendMessage(workerId, request));
	}

	@PutMapping("/{messageId}")
	public CustomResponseEntity<Void> visible(
		@PathVariable final long messageId
	) {
		messageService.visible(messageId);
		return CustomResponseEntity.success();
	}
}
