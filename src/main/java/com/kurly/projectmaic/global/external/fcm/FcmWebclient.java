package com.kurly.projectmaic.global.external.fcm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class FcmWebclient {

	@Value("${fcmkey}")
	private String authorizationKey;

	@Bean
	public WebClient fcmWebclientApi() {
		return WebClient.builder().baseUrl("https://fcm.googleapis.com/fcm/send")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.defaultHeader(HttpHeaders.AUTHORIZATION, "key=" + authorizationKey)
			.build();
	}
}
