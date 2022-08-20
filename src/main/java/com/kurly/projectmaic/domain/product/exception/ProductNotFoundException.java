package com.kurly.projectmaic.domain.product.exception;

import com.kurly.projectmaic.global.common.response.ResponseCode;
import com.kurly.projectmaic.global.error.exception.MaicException;

public class ProductNotFoundException extends MaicException {

	public ProductNotFoundException(ResponseCode code, String message) {
		super(code, message);
	}
}
