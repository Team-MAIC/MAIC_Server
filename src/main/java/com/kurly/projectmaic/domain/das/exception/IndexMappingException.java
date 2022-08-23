package com.kurly.projectmaic.domain.das.exception;

import com.kurly.projectmaic.global.common.response.ResponseCode;
import com.kurly.projectmaic.global.error.exception.MaicException;

public class IndexMappingException extends MaicException {

	public IndexMappingException(ResponseCode code, String message) {
		super(code, message);
	}
}
