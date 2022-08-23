package com.kurly.projectmaic.domain.das.dto.response;

import com.kurly.projectmaic.domain.das.enumeration.BasketColor;
import com.kurly.projectmaic.domain.das.enumeration.BasketStatus;

public record BasketColorResponse(
	long dasTodoId,
	long centerId,
	long roundId,
	long productId,
	String productName,
	double basketWeight,
	int productAmount,
	BasketStatus status,
	BasketColor color
) {
}
