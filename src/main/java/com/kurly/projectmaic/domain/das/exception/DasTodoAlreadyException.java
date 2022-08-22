package com.kurly.projectmaic.domain.das.exception;

import com.kurly.projectmaic.global.common.response.ResponseCode;
import com.kurly.projectmaic.global.error.exception.MaicException;

public class DasTodoAlreadyException extends MaicException {

	public DasTodoAlreadyException(ResponseCode code, String message) {
		super(code, message);
	}
}
