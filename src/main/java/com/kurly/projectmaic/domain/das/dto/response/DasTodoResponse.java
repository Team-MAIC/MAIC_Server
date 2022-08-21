package com.kurly.projectmaic.domain.das.dto.response;

import com.kurly.projectmaic.domain.das.enumeration.BasketColor;

public record DasTodoResponse(
	long roundId,
	long productId,
	String productName,
	int productAmount,
	BasketColor color
) {
}
