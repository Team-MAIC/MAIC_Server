package com.kurly.projectmaic.domain.center.dao;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurly.projectmaic.domain.center.dto.WorkerInfoDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisWorkerRepository {

	private static final String WORKER_INFO_KEY_PATTERN = "worker:%s";

	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;

	public WorkerInfoDto getWorkerInfo(final long workerId) {
		String json = (String) redisTemplate.opsForValue().get(String.format(WORKER_INFO_KEY_PATTERN, workerId));

		if (json == null) {
			return null;
		}

		try {
			return objectMapper.readValue(json, WorkerInfoDto.class);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	public void putWorkerInfo(final WorkerInfoDto workerInfo) {
		String key = String.format(WORKER_INFO_KEY_PATTERN, workerInfo.getWorkerId());

		try {
			String json = objectMapper.writeValueAsString(objectMapper.convertValue(workerInfo, Map.class));
			redisTemplate.opsForValue().set(key, json);
			redisTemplate.expire(key, 86400L, TimeUnit.SECONDS);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
	}
}
