package com.kurly.projectmaic.domain.das.dao.querydsl;

import static com.kurly.projectmaic.domain.das.domain.QDasTodo.*;
import static com.kurly.projectmaic.global.common.response.ResponseCode.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kurly.projectmaic.domain.das.domain.DasTodo;
import com.kurly.projectmaic.domain.das.enumeration.BasketColor;
import com.kurly.projectmaic.domain.das.enumeration.BasketStatus;
import com.kurly.projectmaic.domain.model.StatusType;
import com.kurly.projectmaic.domain.order.dto.querydsl.OrderProductDto;
import com.kurly.projectmaic.domain.product.dto.querydsl.ProductDto;
import com.kurly.projectmaic.domain.product.exception.ProductNotFoundException;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DasTodoQueryDslImpl implements DasTodoQueryDsl {

	private final JPAQueryFactory queryFactory;
	private final JdbcTemplate jdbcTemplate;

	@Override
	public void bulkSave(final long roundId,
		final List<OrderProductDto> orderProductDtos,
		List<ProductDto> products,
		final Map<Long, Integer> baskets) {

		long now = Instant.now().toEpochMilli();

		jdbcTemplate.batchUpdate("INSERT INTO das_todo ("
				+ "round_id, order_info_id, product_id, product_name, product_weight, product_amount, basket_num, basket_weight, status, created_at, modified_at"
				+ ") VALUES ("
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					OrderProductDto dto = orderProductDtos.get(i);
					ProductDto productDto = getProductDto(dto.productId());

					ps.setLong(1, roundId);
					ps.setLong(2, dto.orderInfoId());
					ps.setLong(3, dto.productId());
					ps.setString(4, productDto.productName());
					ps.setDouble(5, productDto.weight());
					ps.setDouble(6, dto.amount());
					ps.setInt( 7, baskets.get(dto.orderInfoId()));
					ps.setDouble(8, 0);
					ps.setString(9, StatusType.READY.name());
					ps.setLong(10, now);
					ps.setLong(11, now);
				}

				private ProductDto getProductDto(final long productId) {
					return products.stream()
						.filter(product -> product.productId() == productId)
						.findFirst()
						.orElseThrow(() -> new ProductNotFoundException(NOT_FOUND_PRODUCT, String.format("productId : %s", productId)));
				}

				@Override
				public int getBatchSize() {
					return orderProductDtos.size();
				}
			});
	}

	public List<DasTodo> getDasTodos(final long roundId, final BasketStatus status, final BasketColor color) {
		BasketStatus basketStatus = status;

		if (color == BasketColor.BLACK) {
			basketStatus = BasketStatus.WRONG;
		}

		return queryFactory.select(dasTodo)
			.from(dasTodo)
			.where(
				dasTodo.roundId.eq(roundId),
				eqStatus(basketStatus),
				eqColor(color)
			)
			.orderBy(dasTodo.modifiedAt.asc())
			.fetch();
	}

	public List<BasketColor> getUsedColor(final long roundId) {
		var dasTodos = queryFactory.select(dasTodo)
			.from(dasTodo)
			.where(
				dasTodo.roundId.eq(roundId),
				dasTodo.status.eq(BasketStatus.READY),
				dasTodo.basketColor.isNotNull(),
				eqColor(BasketColor.ALL)
			)
			.groupBy(dasTodo.basketColor)
			.fetch();

		return dasTodos.stream()
			.map(DasTodo::getBasketColor)
			.toList();
	}

	private BooleanExpression eqStatus(BasketStatus status) {
		if (status == BasketStatus.ALL) {
			return dasTodo.status.isNotNull();
		}

		return dasTodo.status.eq(status);
	}

	private BooleanExpression eqColor(BasketColor color) {
		if (color == BasketColor.ALL) {
			return dasTodo.basketColor.isNotNull();
		}

		if (color == BasketColor.BLACK) {
			return null;
		}

		return dasTodo.basketColor.eq(color);
	}

	public void updateColor(final long roundId, final long productId, final BasketColor color) {
		queryFactory.update(dasTodo)
			.set(dasTodo.basketColor, color)
			.set(dasTodo.modifiedAt, Instant.now().toEpochMilli())
			.where(
				dasTodo.roundId.eq(roundId),
				dasTodo.productId.eq(productId)
			)
			.execute();
	}
}
