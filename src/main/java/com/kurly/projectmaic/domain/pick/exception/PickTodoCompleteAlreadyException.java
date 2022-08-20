package com.kurly.projectmaic.domain.pick.exception;

import com.kurly.projectmaic.global.common.response.ResponseCode;
import com.kurly.projectmaic.global.error.exception.MaicException;

public class PickTodoCompleteAlreadyException extends MaicException {

	public PickTodoCompleteAlreadyException(ResponseCode code,
		String message) {
		super(code, message);
	}
}
