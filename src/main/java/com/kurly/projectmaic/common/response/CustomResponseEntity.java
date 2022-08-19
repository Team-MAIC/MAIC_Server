package com.kurly.projectmaic.common.response;

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
                ResponseCode.OK.getCode(), null, data);
    }

    public static <T> CustomResponseEntity<T> fail(String message) {
        return new CustomResponseEntity<>(
                ResponseCode.FAIL.getCode(),null, null);
    }

}
