package com.kurly.projectmaic.domain.pick.dto.querydsl;

import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.querydsl.core.annotations.QueryProjection;

public record PickTodoDto(
	long pickTodoId,
	long productId,
	String productName,
	CenterProductArea area,
	int line,
	int location,
	long amount
) {

	@QueryProjection
	public PickTodoDto {
	}
}
