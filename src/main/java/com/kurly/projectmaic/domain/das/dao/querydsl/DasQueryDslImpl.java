package com.kurly.projectmaic.domain.das.dao.querydsl;

import static com.kurly.projectmaic.domain.das.domain.QDas.*;

import org.springframework.stereotype.Repository;

import com.kurly.projectmaic.domain.das.domain.Das;
import com.kurly.projectmaic.domain.das.domain.QDas;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DasQueryDslImpl implements DasQueryDsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public Das getCurrentDas() {
		return queryFactory.select(das)
			.from(das)
			.where(
				das.status.isNull()
			)
			.orderBy(das.roundId.asc())
			.fetchFirst();
	}
}
