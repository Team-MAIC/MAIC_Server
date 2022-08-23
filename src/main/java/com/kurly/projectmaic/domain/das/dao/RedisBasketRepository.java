package com.kurly.projectmaic.domain.das.dao;

import com.kurly.projectmaic.domain.das.dto.request.BasketMappingRequest;
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
			redisTemplate.opsForValue().set(String.format("%s:%s:%s", centerId, passage, basket.bascketNum()), basket.clientIdx());
		});
	}
}
