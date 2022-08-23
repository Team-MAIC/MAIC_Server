package com.kurly.projectmaic.domain.das.dto.response;

public record BasketInfoResponse(
	BasketMappingResponse idx,
	DasTodoResponse todo
) {
}
