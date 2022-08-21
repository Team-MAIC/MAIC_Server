package com.kurly.projectmaic.domain.order.exception;

import com.kurly.projectmaic.global.common.response.ResponseCode;
import com.kurly.projectmaic.global.error.exception.MaicException;

public class PurchaseException extends MaicException {

	public PurchaseException(ResponseCode code, String message) {
		super(code, message);
	}
}
