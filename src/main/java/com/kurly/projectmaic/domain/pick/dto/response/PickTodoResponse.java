package com.kurly.projectmaic.domain.pick.dto.response;

import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.kurly.projectmaic.domain.model.StatusType;

public record PickTodoResponse(
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
}
