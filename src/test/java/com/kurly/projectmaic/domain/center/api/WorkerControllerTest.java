package com.kurly.projectmaic.domain.center.api;

import com.kurly.projectmaic.ControllerTest;
import com.kurly.projectmaic.domain.center.application.WorkerService;
import com.kurly.projectmaic.domain.center.dto.response.WorkerInfoResponse;
import com.kurly.projectmaic.domain.center.enumeration.WorkerRole;
import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.kurly.projectmaic.domain.product.api.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkerController.class)
class WorkerControllerTest extends ControllerTest {

	@MockBean
	private WorkerService workerService;

    @Test
    void testGetWorkerInfo() throws Exception {

        // given
		WorkerInfoResponse response = new WorkerInfoResponse(
			1L,
			1L,
			"김포물류센터",
			WorkerRole.PICK,
			null,
			CenterProductArea.A
		);

		given(workerService.getWorkerInfo(1L))
			.willReturn(response);

		ResultActions result = mockMvc.perform(
			get("/workers")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.header(
					"worker-id",
					1
				)
		);

		result.andExpect(status().isOk())
			.andDo(
				document("worker-controller-test/test-get-worker-info",
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					requestHeaders(
						headerWithName("worker-id").description("작업자 id")
					),
					responseFields(
						fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
						fieldWithPath("data.workerId").type(JsonFieldType.NUMBER).description("작업자 id"),
						fieldWithPath("data.centerId").type(JsonFieldType.NUMBER).description("센터 id"),
						fieldWithPath("data.centerName").type(JsonFieldType.STRING).description("센터명"),
						fieldWithPath("data.role").type(JsonFieldType.STRING).description("역활"),
						fieldWithPath("data.passage").optional().type(JsonFieldType.STRING).description("통로"),
						fieldWithPath("data.area").type(JsonFieldType.STRING).description("구역")
					)
				)
			);
    }

    @Test
    void updateDeviceToken() {
    }
}
