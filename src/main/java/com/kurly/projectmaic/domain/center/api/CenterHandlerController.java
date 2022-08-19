package com.kurly.projectmaic.domain.center.api;

import com.kurly.projectmaic.domain.center.exception.WorkerNotFoundException;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CenterHandlerController {

    @ExceptionHandler(WorkerNotFoundException.class)
    public CustomResponseEntity<Void> handleWorkerNotFoundException(WorkerNotFoundException e) {
        log.error("workerNotFoundException: {}", e.getCode());
        return CustomResponseEntity.fail(e.getCode());
    }
}
