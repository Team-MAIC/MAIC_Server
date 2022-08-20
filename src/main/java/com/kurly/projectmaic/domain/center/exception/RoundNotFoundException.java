package com.kurly.projectmaic.domain.center.exception;

import com.kurly.projectmaic.global.common.response.ResponseCode;
import com.kurly.projectmaic.global.error.exception.MaicException;

public class RoundNotFoundException extends MaicException {

	public RoundNotFoundException(ResponseCode code, String message) {
		super(code, message);
	}
}
