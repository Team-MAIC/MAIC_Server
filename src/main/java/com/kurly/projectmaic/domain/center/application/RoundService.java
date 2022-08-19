package com.kurly.projectmaic.domain.center.application;

import com.kurly.projectmaic.domain.center.dao.RoundRepository;
import com.kurly.projectmaic.domain.center.dao.WorkerRepository;
import com.kurly.projectmaic.domain.center.domain.Worker;
import com.kurly.projectmaic.domain.center.dto.querydsl.RoundDto;
import com.kurly.projectmaic.domain.center.dto.response.RoundResponse;
import com.kurly.projectmaic.domain.center.dto.response.RoundsResponse;
import com.kurly.projectmaic.domain.center.exception.WorkerNotFoundException;
import com.kurly.projectmaic.domain.pick.application.PickTodoService;
import com.kurly.projectmaic.domain.pick.dto.querydsl.PickTodoCountDto;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodoCountByRoundResponse;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodoCountResponse;
import com.kurly.projectmaic.global.common.response.ResponseCode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundService {

    private final RoundRepository roundRepository;
	private final WorkerRepository workerRepository;
	private final PickTodoService pickTodoService;

	@Transactional(readOnly = true)
    public RoundsResponse getRounds(final long workerId) {
		Worker worker = workerRepository.findById(workerId)
			.orElseThrow(() ->
				new WorkerNotFoundException(ResponseCode.NOT_FOUND_WORKER,
					String.format("workerId : %s", workerId)));

        List<RoundDto> roundDtos = roundRepository.findToDoRoundsByCenterId(worker.getCenter().getCenterId());

		List<Long> roundIds = roundDtos.stream()
			.map(RoundDto::roundId)
			.toList();

		PickTodoCountByRoundResponse response = pickTodoService.getTodoCountByCurrentRound(roundIds, worker.getArea());

		List<RoundResponse> rounds = response.todos().stream()
			.map(todo -> getRoundResponse(roundDtos, todo))
			.toList();

        return new RoundsResponse(rounds);
    }

	private RoundResponse getRoundResponse(List<RoundDto> roundDtos, PickTodoCountResponse todo) {
		RoundDto roundDto = roundDtos.stream()
			.filter(dto -> dto.roundId() == todo.roundId())
			.findFirst()
			.get();

		return new RoundResponse(roundDto.roundId(), roundDto.centerId(), roundDto.centerRoundNumber(), todo.count());
	}
}
