package com.kurly.projectmaic.common.response;

import lombok.Getter;

@Getter
public enum ResponseCode {
    OK(1, "성공"),
    FAIL(-1, "실패");

    private Integer code;
    private String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
