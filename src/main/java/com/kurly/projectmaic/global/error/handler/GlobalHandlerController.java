package com.kurly.projectmaic.global.error.handler;

import com.kurly.projectmaic.domain.das.exception.DasTodoAlreadyException;
import com.kurly.projectmaic.domain.das.exception.DasTodoFoundNotException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kurly.projectmaic.domain.center.exception.RoundNotFoundException;
import com.kurly.projectmaic.domain.center.exception.WorkerNotFoundException;
import com.kurly.projectmaic.domain.das.exception.DasNotFoundException;
import com.kurly.projectmaic.domain.order.exception.PurchaseException;
import com.kurly.projectmaic.domain.pick.exception.PickTodoCompleteAlreadyException;
import com.kurly.projectmaic.domain.pick.exception.PickTodoNotFoundException;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalHandlerController {

	@ExceptionHandler(RoundNotFoundException.class)
	public CustomResponseEntity<Void> handleRoundNotFoundException(RoundNotFoundException e) {
		log.error("roundNotFoundException: {}", e.getCode());
		return CustomResponseEntity.fail(e.getCode());
	}

	@ExceptionHandler(WorkerNotFoundException.class)
	public CustomResponseEntity<Void> handleWorkerNotFoundException(WorkerNotFoundException e) {
		log.error("workerNotFoundException: {}", e.getCode());
		return CustomResponseEntity.fail(e.getCode());
	}

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

	@ExceptionHandler(DasNotFoundException.class)
	public CustomResponseEntity<Void> handleDasNotFoundException(DasNotFoundException e) {
		log.error("dasNotFoundException: {}", e.getCode());
		return CustomResponseEntity.fail(e.getCode());
	}

	@ExceptionHandler(DasTodoAlreadyException.class)
	public CustomResponseEntity<Void> handleDasTodoAlreadyException(DasTodoAlreadyException e) {
		log.error("dasTodoAlreadyException: {}", e.getCode());
		return CustomResponseEntity.fail(e.getCode());
	}

	@ExceptionHandler(DasTodoFoundNotException.class)
	public CustomResponseEntity<Void> handleDasTodoAlreadyException(DasTodoFoundNotException e) {
		log.error("dasTodoFoundNotException: {}", e.getCode());
		return CustomResponseEntity.fail(e.getCode());
	}

	@ExceptionHandler(PurchaseException.class)
	public CustomResponseEntity<Void> handlePurchaseException(PurchaseException e) {
		log.error("purchaseException: {}", e.getCode());
		return CustomResponseEntity.fail(e.getCode());
	}
}
