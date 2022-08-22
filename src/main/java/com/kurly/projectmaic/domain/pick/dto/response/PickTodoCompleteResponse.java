package com.kurly.projectmaic.domain.pick.dto.response;

import com.kurly.projectmaic.global.common.response.SocketResponse;
import com.kurly.projectmaic.global.common.response.SocketResponseType;

import lombok.Getter;

@Getter
public class PickTodoCompleteResponse extends SocketResponse {

	private final long pickTodoId;
	private final long workerId;

	public PickTodoCompleteResponse(SocketResponseType type, long pickTodoId, long workerId) {
		super(type);
		this.pickTodoId = pickTodoId;
		this.workerId = workerId;
	}
}
