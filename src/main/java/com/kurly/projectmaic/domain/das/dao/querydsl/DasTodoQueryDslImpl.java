package com.kurly.projectmaic.domain.das.dao.querydsl;

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

import com.kurly.projectmaic.domain.model.StatusType;
import com.kurly.projectmaic.domain.order.dto.querydsl.OrderProductDto;
import com.kurly.projectmaic.domain.product.dto.querydsl.ProductDto;
import com.kurly.projectmaic.domain.product.exception.ProductNotFoundException;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DasTodoQueryDslImpl implements DasTodoQueryDsl {

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
}
