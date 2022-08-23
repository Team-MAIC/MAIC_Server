package com.kurly.projectmaic.global.external.fcm.request;

public record FcmRequest(
	String to,
	String priority,
	FcmDataRequest data
) {
}
