package com.kurly.projectmaic.domain.pick.dao.querydsl;

import static com.kurly.projectmaic.domain.pick.domain.QPickTodo.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.kurly.projectmaic.domain.model.StatusType;
import com.kurly.projectmaic.domain.pick.dto.querydsl.PickTodoCountDto;
import com.kurly.projectmaic.domain.pick.dto.querydsl.QPickTodoCountDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PickTodoQueryDslImpl implements PickTodoQueryDsl {

    private final JPAQueryFactory queryFactory;

	@Override
    public List<PickTodoCountDto> getTodoCountByCurrentRound(final List<Long> roundIds, final CenterProductArea area) {
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
}
