package com.kurly.projectmaic.domain.center.dto.response;

public record MessageResponse (
	long messageId,
	String content,
	String fullLocation
) {
}
