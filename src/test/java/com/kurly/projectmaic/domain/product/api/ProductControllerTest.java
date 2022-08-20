package com.kurly.projectmaic.domain.product.api;

import com.kurly.projectmaic.ControllerTest;
import com.kurly.projectmaic.domain.product.application.ProductService;
import com.kurly.projectmaic.domain.product.dto.response.ProductInfoResponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest extends ControllerTest{

	@MockBean
	private ProductService productService;

	@Test
	void testGetProductInfo() throws Exception {

		// given
		ProductInfoResponse response = new ProductInfoResponse(
			1L,
			"친환경 노루궁뎅이 버섯"
		);

		// when
		given(productService.getProductInfo("2304023748221"))
			.willReturn(response);

		ResultActions result = mockMvc.perform(
			get("/products/{barcode}", "2304023748221")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		result.andExpect(status().isOk())
			.andDo(
				document("product-controller-test/test-get-product-info",
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					pathParameters(
						parameterWithName("barcode").description("바코드 번호")
					),
					responseFields(
						fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
						fieldWithPath("data.productId").type(JsonFieldType.NUMBER).description("상품 id"),
						fieldWithPath("data.productName").type(JsonFieldType.STRING).description("상품 이름")
					)
			));
	}

}
