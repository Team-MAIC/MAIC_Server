package com.kurly.projectmaic.domain.order.dto.querydsl;

import com.querydsl.core.annotations.QueryProjection;

public record OrderProductDto(
	long orderProductId,
	long orderInfoId,
	long productId,
	long amount
) {

	@QueryProjection
	public OrderProductDto {
	}
}
