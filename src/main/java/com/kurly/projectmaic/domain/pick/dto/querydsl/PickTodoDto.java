package com.kurly.projectmaic.domain.pick.dto.querydsl;

import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.kurly.projectmaic.domain.model.StatusType;
import com.querydsl.core.annotations.QueryProjection;

public record PickTodoDto(
	long pickTodoId,
	long productId,
	String productName,
	String productThumbnail,
	CenterProductArea area,
	int line,
	int location,
	long amount,
	StatusType status
) {

	@QueryProjection
	public PickTodoDto {
	}
}
