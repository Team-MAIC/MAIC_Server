package com.kurly.projectmaic.domain.das.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kurly.projectmaic.domain.das.dao.DasRepository;
import com.kurly.projectmaic.domain.das.domain.Das;
import com.kurly.projectmaic.domain.das.dto.DasRoundResponse;
import com.kurly.projectmaic.domain.order.exception.DasNotFoundException;
import com.kurly.projectmaic.global.common.response.ResponseCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DasService {

	private final DasRepository dasRepository;

	@Transactional
	public DasRoundResponse getDasRounds(final int passage) {
		Das das = getDasByPassage(passage);

		

		return new DasRoundResponse();
	}

	private Das getDasByPassage(int passage) {
		Das das = dasRepository.findByPassage(passage)
			.orElse(null);

		if (das == null) {
			das = dasRepository.getCurrentDas();
		}

		if (das == null) {
			throw new DasNotFoundException(ResponseCode.NOT_FOUND_DAS, "");
		}

		das.register(passage);
		dasRepository.save(das);

		return das;
	}
}
