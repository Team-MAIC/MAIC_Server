package com.kurly.projectmaic.domain.das.dto.response;

import com.kurly.projectmaic.domain.das.enumeration.BasketColor;
import com.kurly.projectmaic.domain.das.enumeration.BasketStatus;

public record BasketColorResponse(
	long dasTodoId,
	BasketStatus status,
	BasketColor color,
	long amount
) {
}
