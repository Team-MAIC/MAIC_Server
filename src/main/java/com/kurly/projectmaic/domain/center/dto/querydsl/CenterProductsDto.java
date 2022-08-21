package com.kurly.projectmaic.domain.center.dto.querydsl;

import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.querydsl.core.annotations.QueryProjection;

public record CenterProductsDto(
	long productId,
	CenterProductArea area,
	int line,
	int location
) {

	@QueryProjection
	public CenterProductsDto {
	}
}
