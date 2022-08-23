package com.kurly.projectmaic.domain.das.dto.request;

import com.kurly.projectmaic.domain.das.enumeration.BasketColor;
import com.kurly.projectmaic.domain.das.enumeration.BasketStatus;

public record BasketInternalRequest(
	Long dasTodoId,
	long centerId,
	int passage,
	long roundId,
	int basketNum,
	long productId,
	String productName,
	double basketWeight,
	double currentBasketWeight,
	int productAmount,
	double productWeight,
	BasketStatus status,
	BasketColor color
) {
}
