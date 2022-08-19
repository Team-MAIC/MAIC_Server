package com.kurly.projectmaic.global.error.exception;

import com.kurly.projectmaic.global.common.response.ResponseCode;
import lombok.Getter;

@Getter
public class MaicException extends RuntimeException {

    private final ResponseCode code;

    public MaicException(ResponseCode code, String message) {
        super(message);
        this.code = code;
    }
}
