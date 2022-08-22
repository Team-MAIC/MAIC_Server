package com.kurly.projectmaic.domain.das.dto.response;

import com.kurly.projectmaic.domain.das.enumeration.BasketColor;

public record ProductsColorResponse(
	BasketColor color,
	String productName
) {
}
