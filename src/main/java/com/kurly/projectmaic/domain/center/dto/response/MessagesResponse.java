package com.kurly.projectmaic.domain.center.dto.response;

import java.util.List;

public record MessagesResponse(
	List<MessageResponse> messages
) {
}
