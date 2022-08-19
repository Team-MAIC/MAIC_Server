package com.kurly.projectmaic.domain.pick.dto.request;

import com.kurly.projectmaic.domain.model.CenterProductArea;

public record PickTodosRequest(
	long roundId,
	CenterProductArea area
) {
}
