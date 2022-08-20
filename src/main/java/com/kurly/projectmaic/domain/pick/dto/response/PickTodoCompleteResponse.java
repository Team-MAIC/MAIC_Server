package com.kurly.projectmaic.domain.pick.dto.response;

import com.kurly.projectmaic.global.common.response.SocketResponse;
import com.kurly.projectmaic.global.common.response.SocketResponseType;

import lombok.Getter;

@Getter
public class PickTodoCompleteResponse extends SocketResponse {

	private final long pickTodoId;

	public PickTodoCompleteResponse(SocketResponseType type, long pickTodoId) {
		super(type);
		this.pickTodoId = pickTodoId;
	}
}
