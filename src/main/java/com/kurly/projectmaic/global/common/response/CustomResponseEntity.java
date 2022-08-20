package com.kurly.projectmaic.global.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomResponseEntity<T> {
    private Integer code;
    private String message;
    private T data;

    public CustomResponseEntity(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> CustomResponseEntity<T> success(T data) {
        return new CustomResponseEntity<>(
                ResponseCode.OK.getCode(), ResponseCode.OK.getMessage(), data);
    }

    public static <Void> CustomResponseEntity<Void> success() {
        return new CustomResponseEntity<>(
                ResponseCode.OK.getCode(), ResponseCode.OK.getMessage(), null);
    }

    public static <T> CustomResponseEntity<T> fail(String message) {
        return new CustomResponseEntity<>(
                ResponseCode.FAIL.getCode(),null, null);
    }

    public static <Void> CustomResponseEntity<Void> fail(ResponseCode responseCode) {
        return new CustomResponseEntity<>(
                responseCode.getCode(),responseCode.getMessage(), null);
    }

	public static <Void> CustomResponseEntity<Void> disconnect() {
		return new CustomResponseEntity<>(
			ResponseCode.DISCONNECT.getCode(), null, null);
	}
}
