package com.kurly.projectmaic.domain.center.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kurly.projectmaic.domain.center.enumeration.WorkerRole;
import com.kurly.projectmaic.domain.model.CenterProductArea;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorkerInfoDto implements Serializable {

	@JsonProperty("workerId")
	final long workerId;
	@JsonProperty("centerId")
	final Long centerId;
	@JsonProperty("centerName")
	final String centerName;
	@JsonProperty("role")
	final WorkerRole role;
	@JsonProperty("passage")
	final Integer passage;
	@JsonProperty("area")
	final CenterProductArea area;
}
