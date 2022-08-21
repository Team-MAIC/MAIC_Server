package com.kurly.projectmaic.domain.order.dto.querydsl;

import com.querydsl.core.annotations.QueryProjection;

public record OrderInfoIdByRoundDto(
	long orderId
) {

	@QueryProjection
	public OrderInfoIdByRoundDto {
	}
}
