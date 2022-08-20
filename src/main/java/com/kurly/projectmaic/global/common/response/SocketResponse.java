package com.kurly.projectmaic.global.common.response;

import lombok.Getter;

@Getter
public class SocketResponse {

	private final int type;
	private final String message;

	public SocketResponse(SocketResponseType type) {
		this.type = type.getType();
		this.message = type.getMessage();
	}
}
