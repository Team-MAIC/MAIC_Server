package com.kurly.projectmaic.domain.das.dto.querydsl;

import com.kurly.projectmaic.domain.das.enumeration.BasketColor;
import com.querydsl.core.annotations.QueryProjection;

public record ProductsColorDto(
	BasketColor color,
	String productName
) {

	@QueryProjection
	public ProductsColorDto {
	}
}
