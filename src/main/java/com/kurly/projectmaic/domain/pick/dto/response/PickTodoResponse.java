package com.kurly.projectmaic.domain.pick.dto.response;

import com.kurly.projectmaic.domain.model.CenterProductArea;

public record PickTodoResponse(
	long pickTodoId,
	long productId,
	String productName,
	CenterProductArea area,
	int line,
	int location,
	long amount
) {
}
