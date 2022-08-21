package com.kurly.projectmaic.domain.pick.dao.querydsl;

import static com.kurly.projectmaic.domain.pick.domain.QPickTodo.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

import com.kurly.projectmaic.domain.center.dto.querydsl.CenterProductDto;
import com.kurly.projectmaic.domain.order.dto.querydsl.OrderProductByRoundIdDto;
import com.kurly.projectmaic.domain.pick.exception.PickTodoFilterType;
import com.kurly.projectmaic.domain.product.dto.querydsl.ProductDto;
import com.kurly.projectmaic.global.common.expression.OrderByNull;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.kurly.projectmaic.domain.model.StatusType;
import com.kurly.projectmaic.domain.pick.dto.querydsl.PickTodoCountDto;
import com.kurly.projectmaic.domain.pick.dto.querydsl.PickTodoDto;
import com.kurly.projectmaic.domain.pick.dto.querydsl.QPickTodoCountDto;
import com.kurly.projectmaic.domain.pick.dto.querydsl.QPickTodoDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PickTodoQueryDslImpl implements PickTodoQueryDsl {

    private final JPAQueryFactory queryFactory;
	private final JdbcTemplate jdbcTemplate;

	@Override
	public void bulkSave(
		final long roundId,
		final List<OrderProductByRoundIdDto> orderProducts,
		final List<ProductDto> productDtos,
		final List<CenterProductDto> centerProductDtos) {

		long now = Instant.now().toEpochMilli();

		jdbcTemplate.batchUpdate("INSERT INTO pick_todo ("
				+ "round_id, product_id, product_name, product_thumbnail, amount, area, line, location, status, created_at, modified_at"
				+ ") values ("
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setLong(1, roundId);
					ps.setLong(2, orderProducts.get(i).productId());
					ps.setString(3, productDtos.get(i).productName());
					ps.setString(4, productDtos.get(i).productThumbnail());
					ps.setLong(5, orderProducts.get(i).amount());
					ps.setString(6, centerProductDtos.get(i).area().name());
					ps.setInt(7, centerProductDtos.get(i).line());
					ps.setInt(8, centerProductDtos.get(i).location());
					ps.setString(9, StatusType.READY.name());
					ps.setLong(10, now);
					ps.setLong(11, now);
				}

				@Override
				public int getBatchSize() {
					return orderProducts.size();
				}
			});
	}

	@Override
	public PickTodoCountDto getTodoCountByCurrentRound(final long roundId) {
		var pickTodoCountDto = queryFactory.select(
				new QPickTodoCountDto(
					pickTodo.roundId,
					pickTodo.count()
				)
			)
			.from(pickTodo)
			.where(
				pickTodo.status.eq(StatusType.READY),
				pickTodo.roundId.eq(roundId)
			)
			.groupBy(pickTodo.roundId, pickTodo.area)
			.orderBy(OrderByNull.DEFAULT)
			.fetchFirst();

		return (pickTodoCountDto == null) ?
			new PickTodoCountDto(roundId, 0L) : pickTodoCountDto;
	}

	@Override
    public List<PickTodoCountDto> getTodoCountByRounds(final List<Long> roundIds, final CenterProductArea area) {
		return queryFactory.select(
				new QPickTodoCountDto(
					pickTodo.roundId,
					pickTodo.count()
				)
			)
			.from(pickTodo)
			.where(
				pickTodo.status.eq(StatusType.READY),
				pickTodo.roundId.in(roundIds),
				pickTodo.area.eq(area)
			)
			.groupBy(pickTodo.roundId, pickTodo.area)
			.orderBy(pickTodo.roundId.asc())
			.fetch();
    }

	@Override
	public List<PickTodoDto> getPickTodos(final long roundId, final CenterProductArea area, final long workerId,
		final PickTodoFilterType filterType) {
		return queryFactory.select(
				new QPickTodoDto(
					pickTodo.pickTodoId,
					pickTodo.productId,
					pickTodo.productName,
					pickTodo.productThumbnail,
					pickTodo.area,
					pickTodo.line,
					pickTodo.location,
					pickTodo.amount,
					pickTodo.status
				)
			)
			.from(pickTodo)
			.where(
				isFiltered(workerId, filterType),
				pickTodo.roundId.eq(roundId),
				pickTodo.area.eq(area)
			)
			.orderBy(pickTodo.line.asc(), pickTodo.location.asc())
			.fetch();
	}

	private BooleanExpression isFiltered(final long workerId, final PickTodoFilterType filterType) {
		if (filterType == PickTodoFilterType.ING) {
			return pickTodo.status.eq(StatusType.READY);
		}

		if (filterType == PickTodoFilterType.FINISH) {
			return pickTodo.status.eq(StatusType.FINISH);
		}

		if (filterType == PickTodoFilterType.MY_FINISH) {
			return pickTodo.workerId.eq(workerId);
		}

		return null;
	}
}
