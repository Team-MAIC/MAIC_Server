package com.kurly.projectmaic.domain.das.dao.querydsl;

import java.util.List;
import java.util.Map;

import com.kurly.projectmaic.domain.order.dto.querydsl.OrderProductDto;
import com.kurly.projectmaic.domain.product.dto.querydsl.ProductDto;

public interface DasTodoQueryDsl {
	void bulkSave(final long roundId,
		final List<OrderProductDto> orderProductDtos,
		List<ProductDto> products,
		final Map<Long, Integer> baskets);
}
