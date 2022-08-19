package com.kurly.projectmaic.global.common.response;

import lombok.Getter;

@Getter
public enum ResponseCode {
    OK(1, "성공"),
    FAIL(-1, "실패"),

    // center response code
    NOT_FOUND_WORKER(1001, "작업자 정보를 조회하지 못했습니다.");

    private Integer code;
    private String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
