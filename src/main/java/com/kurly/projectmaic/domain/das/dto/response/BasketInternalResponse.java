package com.kurly.projectmaic.domain.das.dto.response;

import com.kurly.projectmaic.domain.das.enumeration.BasketColor;
import com.kurly.projectmaic.domain.das.enumeration.BasketStatus;

public record BasketInternalResponse(
	Long dasTodoId,
	long centerId,
	int passage,
	long roundId,
	long productId,
	String productName,
	double basketWeight,
	int productAmount,
	double productWeight,
	BasketStatus status,
	BasketColor color
) {
}
