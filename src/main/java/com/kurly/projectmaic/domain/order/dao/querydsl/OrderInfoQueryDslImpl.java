package com.kurly.projectmaic.domain.order.dao.querydsl;

import static com.kurly.projectmaic.domain.order.domain.QOrderInfo.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kurly.projectmaic.domain.order.dto.querydsl.OrderInfoIdByRoundDto;
import com.kurly.projectmaic.domain.order.dto.querydsl.QOrderInfoIdByRoundDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderInfoQueryDslImpl implements OrderInfoQueryDsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<OrderInfoIdByRoundDto> getOrderIdsByRound(final long roundId) {
		return queryFactory.select(
				new QOrderInfoIdByRoundDto(
					orderInfo.orderInfoId
				)
			)
			.from(orderInfo)
			.where(
				orderInfo.roundId.eq(roundId)
			)
			.orderBy(orderInfo.roundId.asc())
			.fetch();
	}
}
