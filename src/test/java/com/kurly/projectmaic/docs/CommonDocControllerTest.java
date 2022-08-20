package com.kurly.projectmaic.docs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kurly.projectmaic.ControllerTest;
import com.kurly.projectmaic.domain.center.api.WorkerController;
import com.kurly.projectmaic.domain.center.application.WorkerService;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;
import com.kurly.projectmaic.global.common.response.ResponseCode;
import org.hibernate.type.EnumType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
public class CommonDocControllerTest extends ControllerTest {

	@MockBean
	WorkerService workerService;

	@Test
	public void enums() throws Exception {

		// when
		ResultActions result = mockMvc.perform(
			get("/docs/enums")
				.contentType(MediaType.APPLICATION_JSON)
		);

		// 결과값
		MvcResult mvcResult = result.andReturn();

		// 데이터 파싱
		EnumDocs enumDocs = getData(mvcResult);

		// then
		result.andExpect(status().isOk())
			.andDo(document("common-doc-controller-test/enums",
				customResponseFields("custom-response", beneathPath("data.responseCode").withSubsectionId("responseCode"),
					attributes(key("title").value("responseCode")),
					enumConvertFieldDescriptor(enumDocs.getResponseCode())
				),
				customResponseFields("custom-response", beneathPath("data.socketResponseType").withSubsectionId("socketResponseType"),
					attributes(key("title").value("socketResponseType")),
					enumConvertFieldDescriptor(enumDocs.getSocketResponseType())
				)
			));
	}

	public static CustomResponseFieldsSnippet customResponseFields(String type, PayloadSubsectionExtractor<?> subsectionExtractor,
																   Map<String, Object> attributes, FieldDescriptor... descriptors) {
		return new CustomResponseFieldsSnippet(type, subsectionExtractor, Arrays.asList(descriptors), attributes
			, true);
	}

	private FieldDescriptor[] enumConvertFieldDescriptor(Map<String, String> enumValues) {
		return enumValues.entrySet().stream()
			.map(x -> fieldWithPath(x.getKey()).description(x.getValue()))
			.toArray(FieldDescriptor[]::new);
	}

	// mvc result 데이터 파싱
	private EnumDocs getData(MvcResult result) throws IOException {
		ApiResponseDto<EnumDocs> apiResponseDto = objectMapper
			.readValue(result.getResponse().getContentAsByteArray(),
				new TypeReference<ApiResponseDto<EnumDocs>>() {}
			);
		return apiResponseDto.getData();
	}
}
