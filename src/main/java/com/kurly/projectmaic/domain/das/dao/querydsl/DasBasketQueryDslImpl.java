package com.kurly.projectmaic.domain.das.dao.querydsl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kurly.projectmaic.domain.order.domain.OrderProduct;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DasBasketQueryDslImpl implements DasBasketQueryDsl {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public void bulkSave(List<Long> orderIds) {
		jdbcTemplate.batchUpdate("INSERT INTO das_basket ("
				+ "order_id, basket_num"
				+ ") VALUES ("
				+ "?, ?)",
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setLong(1, orderIds.get(i));
					ps.setInt(2, i);
				}

				@Override
				public int getBatchSize() {
					return orderIds.size();
				}
			});
	}
}
