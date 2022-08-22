package com.kurly.projectmaic.domain.das.dto.response;

public record BasketInfoResponse(
	int basketNum,
	DasTodoResponse todo
) {
}
