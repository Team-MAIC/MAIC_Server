package com.kurly.projectmaic.domain.das.dto.response;

import java.util.List;

public record DasTodoResponse(
	long roundId,
	List<BasketResponse> baskets
) {
}
