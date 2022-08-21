package com.kurly.projectmaic.domain.center.dto.querydsl;

import com.querydsl.core.annotations.QueryProjection;

public record CurrentRoundDto(
	long roundId,
	long centerRoundNumber
) {

	@QueryProjection
	public CurrentRoundDto {
	}
}
