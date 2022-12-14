package com.kurly.projectmaic.domain.das.dao;

import com.kurly.projectmaic.domain.das.dto.request.BasketMappingRequest;
import com.kurly.projectmaic.domain.das.exception.IndexMappingException;
import com.kurly.projectmaic.global.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RedisBasketRepository {

	private final RedisTemplate<String, Object> redisTemplate;


	public void deleteKeys(long centerId, int passage) {

		redisTemplate.delete(String.format("%s:%s:*", centerId, passage));
	}

	public void setKeys(long centerId, int passage, List<BasketMappingRequest> baskets) {
		baskets.forEach(basket -> {
			redisTemplate.opsForValue().set(String.format("%s:%s:%s", centerId, passage, basket.basketNum()), String.valueOf(basket.clientIdx()));
		});
	}

	public Long getKey(long centerId, int passage, int basketNum) {
		String key = (String) redisTemplate.opsForValue().get(String.format("%s:%s:%s", centerId, passage, basketNum));

		if (key == null) {
			throw new IndexMappingException(ResponseCode.FAIL, String.format("%s:%s:%s", centerId, passage, basketNum));
		}

		return Long.valueOf(key);
	}
}
