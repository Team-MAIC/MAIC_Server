package com.kurly.projectmaic.domain.center.dto.response;

public record RoundResponse(
	long roundId,
	long centerId,
	long centerRoundNumber,
	long pickTodoCount
) {
}
