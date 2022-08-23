package com.kurly.projectmaic.domain.das.dto.response;

import java.util.List;

public record DasTodoSummaryResponse(
	long roundId,
	long centerRoundNumber,
	List<BasketResponse> baskets
) {
}
