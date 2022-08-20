package com.kurly.projectmaic.domain.pick.api;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kurly.projectmaic.domain.pick.exception.PickTodoCompleteAlreadyException;
import com.kurly.projectmaic.domain.pick.exception.PickTodoNotFoundException;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class PickTodoHandlerController {

	@ExceptionHandler(PickTodoNotFoundException.class)
	public CustomResponseEntity<Void> handlePickTodoNotFoundException(PickTodoNotFoundException e) {
		log.error("pickTodoNotFoundException: {}", e.getCode());
		return CustomResponseEntity.fail(e.getCode());
	}

	@ExceptionHandler(PickTodoCompleteAlreadyException.class)
	public CustomResponseEntity<Void> handlePickTodoCompleteAlreadyException(PickTodoCompleteAlreadyException e) {
		log.error("pickTodoCompleteAlreadyException: {}", e.getCode());
		return CustomResponseEntity.fail(e.getCode());
	}
}
