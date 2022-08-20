package com.kurly.projectmaic.domain.pick.application;

import static com.kurly.projectmaic.global.common.constant.SocketDestination.*;

import java.util.List;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kurly.projectmaic.domain.center.application.RoundService;
import com.kurly.projectmaic.domain.center.dao.RoundRepository;
import com.kurly.projectmaic.domain.center.enumeration.RoundStatus;
import com.kurly.projectmaic.domain.center.exception.WorkerNotFoundException;
import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.kurly.projectmaic.domain.model.StatusType;
import com.kurly.projectmaic.domain.pick.dao.PickTodoRepository;
import com.kurly.projectmaic.domain.pick.domain.PickTodo;
import com.kurly.projectmaic.domain.pick.dto.querydsl.PickTodoCountDto;
import com.kurly.projectmaic.domain.pick.dto.querydsl.PickTodoDto;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodoCompleteResponse;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodoCountByRoundResponse;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodoCountResponse;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodoResponse;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodosResponse;
import com.kurly.projectmaic.domain.pick.exception.PickTodoCompleteAlreadyException;
import com.kurly.projectmaic.domain.pick.exception.PickTodoNotFoundException;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;
import com.kurly.projectmaic.global.common.response.ResponseCode;
import com.kurly.projectmaic.global.common.response.SocketResponseType;
import com.kurly.projectmaic.global.common.utils.RedisChannelUtils;
import com.kurly.projectmaic.global.queue.RedisPublisher;
import com.kurly.projectmaic.global.queue.RedisSubscriber;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PickTodoService {

	private final PickTodoRepository pickTodoRepository;
	private final RoundRepository roundRepository;
	private final RedisMessageListenerContainer container;
	private final RedisSubscriber subscriber;
	private final RedisPublisher publisher;

	@Transactional(readOnly = true)
	public PickTodosResponse getPickTodos(final long roundId, final CenterProductArea area) {
		List<PickTodoDto> dtos = pickTodoRepository.getPickTodos(roundId, area);

		List<PickTodoResponse> todos = dtos.stream()
			.map(dto -> new PickTodoResponse(
				dto.pickTodoId(),
				dto.productId(),
				dto.productName(),
				dto.area(),
				dto.line(),
				dto.location(),
				dto.amount()
			))
			.toList();

		return new PickTodosResponse(todos);
	}

	public void subscribePickChannel(final long roundId, final CenterProductArea area) {
		container.addMessageListener(subscriber,
			RedisChannelUtils.getPickTodoTopic(roundId, area));
	}

	@Transactional
	public void finishPickTodo(long workerId, long pickTodoId) {
		PickTodo pickTodo = getPickTodo(pickTodoId);

		pickTodo.complete(workerId);

		pickTodoRepository.save(pickTodo);

		PickTodoCompleteResponse response = new PickTodoCompleteResponse(
			SocketResponseType.PICK_TODO_COMPLETE,
			pickTodoId
		);

		publisher.publish(
			RedisChannelUtils.getPickTodoTopic(pickTodo.getRoundId(), pickTodo.getArea()),
			CustomResponseEntity.success(response)
		);

		// checkCompleted(pickTodo);
	}

	private void checkCompleted(PickTodo pickTodo) {
		PickTodoCountDto todoCount = pickTodoRepository.getTodoCountByCurrentRound(pickTodo.getRoundId());

		if (todoCount.count() == 0) {
			roundRepository.updateRoundStatus(pickTodo.getRoundId(), RoundStatus.DAS);

			publisher.publish(
				RedisChannelUtils.getPickTodoTopic(pickTodo.getRoundId(), pickTodo.getArea()),
				CustomResponseEntity.disconnect()
			);
		}
	}

	private PickTodo getPickTodo(long pickTodoId) {
		PickTodo pickTodo = pickTodoRepository.findById(pickTodoId)
			.orElseThrow(() ->
				new PickTodoNotFoundException(ResponseCode.NOT_FOUND_PICK_TODO,
					String.format("pickTodoId : %s", pickTodoId)));

		if (pickTodo.getStatus() == StatusType.FINISH) {
			throw new PickTodoCompleteAlreadyException(ResponseCode.ALREADY_PICK_TODO,
				String.format("pickTodoId : %s", pickTodoId));
		}

		return pickTodo;
	}
}
