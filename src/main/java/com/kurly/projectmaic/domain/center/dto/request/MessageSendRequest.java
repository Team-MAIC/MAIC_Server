package com.kurly.projectmaic.domain.center.dto.request;

public record MessageSendRequest(
	long roundId,
	long productId,
	String productName,
	long centerRoundNumber,
	long amount
) {
}
