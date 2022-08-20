package com.kurly.projectmaic.global.queue;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisPublisher {

	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;

	public void publish(ChannelTopic topic, Object obj) {
		try {
			String json = objectMapper.writeValueAsString(objectMapper.convertValue(obj, Map.class));
			redisTemplate.convertAndSend(topic.getTopic(), json);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
	}
}
