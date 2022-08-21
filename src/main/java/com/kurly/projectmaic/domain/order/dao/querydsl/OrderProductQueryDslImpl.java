package com.kurly.projectmaic.domain.order.dao.querydsl;

import static com.kurly.projectmaic.domain.order.domain.QOrderInfo.*;
import static com.kurly.projectmaic.domain.order.domain.QOrderProduct.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kurly.projectmaic.domain.order.domain.OrderProduct;
import com.kurly.projectmaic.domain.order.dto.querydsl.OrderProductByRoundIdDto;
import com.kurly.projectmaic.domain.order.dto.querydsl.QOrderProductByRoundIdDto;
import com.kurly.projectmaic.global.common.expression.OrderByNull;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderProductQueryDslImpl implements OrderProductQueryDsl {

	private final JPAQueryFactory queryFactory;
	private final JdbcTemplate jdbcTemplate;

	@Override
	public void bulkSave(List<OrderProduct> products) {
		jdbcTemplate.batchUpdate("INSERT INTO order_product ("
			+ "order_info_id, product_id, amount"
			+ ") values ("
			+ "?, ?, ?)",
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setLong(1, products.get(i).getOrderInfoId());
					ps.setLong(2, products.get(i).getProductId());
					ps.setLong(3, products.get(i).getAmount());
				}

				@Override
				public int getBatchSize() {
					return products.size();
				}
			});
	}

	@Override
	public List<OrderProductByRoundIdDto> getOrderProductsByRoundId(final long roundId) {
		return queryFactory.select(
				new QOrderProductByRoundIdDto(
					orderProduct.productId,
					orderProduct.amount.sum()
				)
			)
			.from(orderProduct)
			.leftJoin(orderInfo)
			.on(orderProduct.orderInfoId.eq(orderInfo.orderInfoId))
			.where(
				orderInfo.roundId.eq(roundId)
			)
			.groupBy(orderProduct.productId)
			.orderBy(OrderByNull.DEFAULT)
			.fetch();
	}
}
