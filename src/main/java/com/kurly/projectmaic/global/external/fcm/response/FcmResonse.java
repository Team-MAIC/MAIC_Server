package com.kurly.projectmaic.global.external.fcm.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record FcmResonse(
	@JsonProperty("multicast_id")
	long multicastId,
	int success,
	int failure,
	@JsonProperty("canonical_ids")
	long canonicalIds,
	List<FcmResultsResponse> results
) {
}
