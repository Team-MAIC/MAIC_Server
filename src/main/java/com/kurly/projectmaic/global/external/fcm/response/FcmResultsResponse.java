package com.kurly.projectmaic.global.external.fcm.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FcmResultsResponse(
	@JsonProperty("message_id")
	String messageId
) {
}
