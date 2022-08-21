package com.kurly.projectmaic.domain.order.dao.querydsl;

import java.util.List;

import com.kurly.projectmaic.domain.order.dto.querydsl.OrderInfoIdByRoundDto;

public interface OrderInfoQueryDsl {

	List<OrderInfoIdByRoundDto> getOrderIdsByRound(final long roundId);
}
