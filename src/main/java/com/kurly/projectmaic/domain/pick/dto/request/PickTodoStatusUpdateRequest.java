package com.kurly.projectmaic.domain.pick.dto.request;

public record PickTodoStatusUpdateRequest(
	long roundId,
	long productId
) {
}
