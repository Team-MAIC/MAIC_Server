package com.kurly.projectmaic.domain.pick.dao.querydsl;

import static com.kurly.projectmaic.domain.pick.domain.QPickTodo.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.kurly.projectmaic.domain.model.StatusType;
import com.kurly.projectmaic.domain.pick.dto.querydsl.PickTodoCountDto;
import com.kurly.projectmaic.domain.pick.dto.querydsl.PickTodoDto;
import com.kurly.projectmaic.domain.pick.dto.querydsl.QPickTodoCountDto;
import com.kurly.projectmaic.domain.pick.dto.querydsl.QPickTodoDto;
import com.kurly.projectmaic.global.common.expression.OrderByNull;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PickTodoQueryDslImpl implements PickTodoQueryDsl {

    private final JPAQueryFactory queryFactory;

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
	public List<PickTodoDto> getPickTodos(final long roundId, final CenterProductArea area) {
		return queryFactory.select(
				new QPickTodoDto(
					pickTodo.pickTodoId,
					pickTodo.productId,
					pickTodo.productName,
					pickTodo.area,
					pickTodo.line,
					pickTodo.location,
					pickTodo.amount
				)
			)
			.from(pickTodo)
			.where(
				pickTodo.roundId.eq(roundId),
				pickTodo.area.eq(area)
			)
			.orderBy(pickTodo.line.asc(), pickTodo.location.asc())
			.fetch();
	}
}
