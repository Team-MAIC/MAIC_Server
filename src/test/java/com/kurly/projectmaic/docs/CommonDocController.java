package com.kurly.projectmaic.docs;

import com.kurly.projectmaic.global.common.response.EnumType;
import com.kurly.projectmaic.global.common.response.ResponseCode;
import com.kurly.projectmaic.global.common.response.SocketResponseType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/docs")
public class CommonDocController {

	@GetMapping("/enums")
	public ApiResponseDto<EnumDocs> findEnums() {

		Map<String, String> responseCode = getDocs(ResponseCode.values());
		Map<String, String> socketResponseType = getDocs(SocketResponseType.values());

		return ApiResponseDto.of(EnumDocs.builder()
			.responseCode(responseCode)
			.socketResponseType(socketResponseType)
			.build()
		);
	}

	private Map<String, String> getDocs(EnumType[] enumTypes) {
		return Arrays.stream(enumTypes)
			.collect(Collectors.toMap(EnumType::getCodeToString, EnumType::getMessage));
	}
}
