package com.kurly.projectmaic.domain.pick.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.kurly.projectmaic.domain.pick.dao.PickTodoRepository;
import com.kurly.projectmaic.domain.pick.dto.querydsl.PickTodoCountDto;
import com.kurly.projectmaic.domain.pick.dto.querydsl.PickTodoDto;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodoCountByRoundResponse;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodoCountResponse;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodoResponse;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodosResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PickTodoService {

	private final PickTodoRepository pickTodoRepository;

	@Transactional(readOnly = true)
	public PickTodoCountByRoundResponse getTodoCountByCurrentRound(final List<Long> roundIds, final CenterProductArea area) {
		List<PickTodoCountDto> pickTodoCountDtos = pickTodoRepository.getTodoCountByCurrentRound(roundIds, area);

		List<PickTodoCountResponse> todos = pickTodoCountDtos.stream()
			.map(dto -> new PickTodoCountResponse(dto.roundId(), dto.count()))
			.toList();

		return new PickTodoCountByRoundResponse(todos);
	}

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
}
