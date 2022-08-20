package com.kurly.projectmaic.domain.product.api;

import com.kurly.projectmaic.domain.product.exception.ProductNotFoundException;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ProductHandlerController {

	@ExceptionHandler(ProductNotFoundException.class)
	public CustomResponseEntity<Void> handleProductNotFoundException(ProductNotFoundException e) {
		log.error("productNotFoundException: {}", e.getCode());
		return CustomResponseEntity.fail(e.getCode());
	}
}
