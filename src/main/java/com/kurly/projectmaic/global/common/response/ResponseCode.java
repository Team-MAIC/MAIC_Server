package com.kurly.projectmaic.global.common.response;

import lombok.Getter;

@Getter
public enum ResponseCode implements EnumType {
    OK(1, "성공"),
    FAIL(-1, "실패"),
	DISCONNECT(-2, "해제"),

    // center response code
    NOT_FOUND_WORKER(1001, "작업자 정보를 조회하지 못했습니다."),

	// pick response code
	NOT_FOUND_PICK_TODO(2001, "항목을 조회하지 못했습니다."),
	ALREADY_PICK_TODO(2002, "이미 완료 된 항목입니다."),

	// product response code
	NOT_FOUND_PRODUCT(3001, "상품 정보를 조회하지 못했습니다."),

	NOT_FOUND_DAS(4001, "DAS 항목을 조회하지 못했습니다."),

	NOT_FOUND_ROUND(5001, "Round 정보를 조회하지 못했습니다.");

    private Integer code;
    private String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

	@Override
	public String getCodeToString() {
		return this.code.toString();
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
