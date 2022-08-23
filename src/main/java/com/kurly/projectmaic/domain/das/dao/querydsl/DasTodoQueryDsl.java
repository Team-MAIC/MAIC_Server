package com.kurly.projectmaic.domain.das.dao.querydsl;

import java.util.List;
import java.util.Map;

import com.kurly.projectmaic.domain.das.domain.DasTodo;
import com.kurly.projectmaic.domain.das.dto.querydsl.ProductsColorDto;
import com.kurly.projectmaic.domain.das.enumeration.BasketColor;
import com.kurly.projectmaic.domain.das.enumeration.BasketStatus;
import com.kurly.projectmaic.domain.order.dto.querydsl.OrderProductDto;
import com.kurly.projectmaic.domain.product.dto.querydsl.ProductDto;

public interface DasTodoQueryDsl {
	void bulkSave(final long centerId, final int passage, final long roundId,
		final List<OrderProductDto> orderProductDtos,
		List<ProductDto> products,
		final Map<Long, Integer> baskets);

	List<DasTodo> getDasTodos(final long roundId, final BasketStatus status, final BasketColor color);

	List<ProductsColorDto> getUsedColor(final long roundId);
	void updateColor(final long roundId, final long productId, final BasketColor color);
	void updateStatus(final long dasTodoId, BasketStatus status);
	DasTodo nextDasTodo(final long centerId, final int passage, final long roundId, final int basketId);

	void completeStatus(final long dasTodoId);
	void updateWeight(final long centerId, final long roundId, final int passage,
					  final int basketNum, final double basketWeight);
}
