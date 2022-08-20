package com.kurly.projectmaic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurly.projectmaic.config.RestDocsConfiguration;
import com.kurly.projectmaic.docs.CommonDocController;
import com.kurly.projectmaic.domain.center.api.WorkerController;
import com.kurly.projectmaic.domain.center.application.WorkerService;
import com.kurly.projectmaic.domain.product.api.ProductController;
import com.kurly.projectmaic.domain.product.application.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@Import(RestDocsConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(CommonDocController.class)
public class ControllerTest {

	protected MockMvc mockMvc;

    @Autowired
    RestDocumentationResultHandler restDocs;

    @Autowired
	public ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider provider){
        this.mockMvc= MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocs)
                .build();
    }
}
