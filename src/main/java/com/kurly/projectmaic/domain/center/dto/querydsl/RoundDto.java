package com.kurly.projectmaic.domain.center.dto.querydsl;

import com.kurly.projectmaic.domain.center.enumeration.RoundStatus;
import com.querydsl.core.annotations.QueryProjection;

public record RoundDto(
	long roundId,
	long centerId,
	long centerRoundNumber,
	RoundStatus status
) {

	@QueryProjection
	public RoundDto {
	}
}
