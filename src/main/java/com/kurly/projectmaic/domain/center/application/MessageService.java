package com.kurly.projectmaic.domain.center.application;

import com.kurly.projectmaic.domain.center.dao.MessageRepository;
import com.kurly.projectmaic.domain.center.dao.WorkerRepository;
import com.kurly.projectmaic.domain.center.domain.Message;
import com.kurly.projectmaic.domain.center.domain.Worker;
import com.kurly.projectmaic.domain.center.dto.request.MessageSendRequest;
import com.kurly.projectmaic.domain.center.dto.response.MessageResponse;
import com.kurly.projectmaic.domain.center.dto.response.MessagesResponse;
import com.kurly.projectmaic.domain.center.exception.WorkerNotFoundException;
import com.kurly.projectmaic.domain.pick.dao.PickTodoRepository;
import com.kurly.projectmaic.global.common.response.ResponseCode;
import com.kurly.projectmaic.global.external.fcm.request.FcmDataRequest;
import com.kurly.projectmaic.global.external.fcm.request.FcmRequest;
import com.kurly.projectmaic.global.external.fcm.response.FcmResonse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final MessageRepository messageRepository;
	private final WorkerRepository workerRepository;
	private final PickTodoRepository pickTodoRepository;
	private final WebClient fcmWebclientApi;

	@Transactional(readOnly = true)
	public MessagesResponse getMessages(final long workerId) {
		List<Message> messages = messageRepository.findMessagesByWorkerIdAndIsVisibleOrderByCreatedAtDesc(workerId, Boolean.TRUE);
		List<MessageResponse> m = messages.stream()
			.map(message -> getMessageResponse(message))
			.toList();

		return new MessagesResponse(m);
	}

	private MessageResponse getMessageResponse(final Message message) {
		return new MessageResponse(
			message.getMessageId(),
			message.getContent(),
			message.getFullLocation()
		);
	}

	@Transactional
	public FcmResonse sendMessage(final long workerId, final MessageSendRequest request) {
		Worker worker = workerRepository.findById(workerId)
			.orElseThrow(() ->
				new WorkerNotFoundException(ResponseCode.NOT_FOUND_WORKER,
					String.format("workerId : %s", workerId)));

		Long pickWorkerId = pickTodoRepository.getPickWorkerId(request.roundId(), request.productId());

		if (pickWorkerId == null) {
			throw new WorkerNotFoundException(ResponseCode.NOT_FOUND_PICK_WORKER,
				String.format("workerId : %s", workerId));
		}

		Worker pickWorker = workerRepository.findById(pickWorkerId)
			.orElseThrow(() ->
				new WorkerNotFoundException(ResponseCode.NOT_FOUND_PICK_WORKER,
					String.format("workerId : %s", workerId)));

		String position = String.format("%s회차 %s", request.centerRoundNumber(), request.position());
		String content = String.format("%s번 통로 %s %s개 부족", worker.getPassage(), request.productName(), request.amount());

		Message message = new Message(
			pickWorker.getWorkerId(),
			content,
			position
		);

		FcmRequest messageSendRequest = new FcmRequest(
			pickWorker.getDeviceToken(),
			"high",
			new FcmDataRequest(
				content,
				position
			)
		);

		messageRepository.save(message);

		return fcmWebclientApi.post()
			.bodyValue(messageSendRequest)
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.bodyToMono(FcmResonse.class)
			.block();
	}

	@Transactional
	public void visible(final long messageId) {
		Message message = messageRepository.findById(messageId)
			.get();

		message.visible();

		messageRepository.save(message);
	}
}
