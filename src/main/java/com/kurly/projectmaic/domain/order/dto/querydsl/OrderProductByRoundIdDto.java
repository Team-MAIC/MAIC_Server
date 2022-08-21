package com.kurly.projectmaic.domain.order.dto.querydsl;

import com.querydsl.core.annotations.QueryProjection;

public record OrderProductByRoundIdDto(
	long productId,
	long amount
) {

	@QueryProjection
	public OrderProductByRoundIdDto {
	}
}
