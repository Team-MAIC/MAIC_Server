package com.kurly.projectmaic.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocketResponseType implements EnumType {
	PICK_TODO_COMPLETE(1001, "상품 피킹 완료.");

	private final int type;
	private final String message;

	@Override
	public String getCodeToString() {
		return String.valueOf(type);
	}
}
