package com.kurly.projectmaic.domain.das.exception;

import com.kurly.projectmaic.global.common.response.ResponseCode;
import com.kurly.projectmaic.global.error.exception.MaicException;

public class DasTodoFoundNotException extends MaicException {

	public DasTodoFoundNotException(ResponseCode code,
		String message) {
		super(code, message);
	}
}
