package com.kurly.projectmaic.domain.pick.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.kurly.projectmaic.domain.pick.dao.PickTodoRepository;
import com.kurly.projectmaic.domain.pick.dto.querydsl.PickTodoCountDto;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodoCountByRoundResponse;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodoCountResponse;

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
}
