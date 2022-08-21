package com.kurly.projectmaic.domain.center.dao.querydsl;

import static com.kurly.projectmaic.domain.center.domain.QRound.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kurly.projectmaic.domain.center.domain.QRound;
import com.kurly.projectmaic.domain.center.domain.Round;
import com.kurly.projectmaic.domain.center.dto.querydsl.CurrentRoundDto;
import com.kurly.projectmaic.domain.center.dto.querydsl.QCurrentRoundDto;
import com.kurly.projectmaic.domain.center.dto.querydsl.QRoundDto;
import com.kurly.projectmaic.domain.center.dto.querydsl.RoundDto;
import com.kurly.projectmaic.domain.center.enumeration.RoundStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RoundQueryDslImpl implements RoundQueryDsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public CurrentRoundDto getCurrentRoundId(long centerId) {
		return queryFactory.select(
				new QCurrentRoundDto(
					round.roundId,
					round.centerRoundNumber
				)
			)
			.from(round)
			.where(
				round.centerId.eq(centerId)
			)
			.orderBy(round.roundId.desc())
			.limit(1)
			.fetchFirst();
	}

	@Override
	public Long createNewRound(long centerId, long centerRoundNumber) {
		return queryFactory.insert(round)
			.columns(round.centerId, round.centerRoundNumber, round.status)
			.values(centerId, centerRoundNumber, RoundStatus.WAIT)
			.execute();
	}

	@Override
	public List<RoundDto> findToDoRoundsByCenterId(long centerId) {
		return queryFactory.select(
				new QRoundDto(
					round.roundId,
					round.centerId,
					round.centerRoundNumber,
					round.status
				)
			)
			.from(round)
			.where(
				round.status.in(List.of(RoundStatus.READY, RoundStatus.PICK))
			)
			.fetch();
	}

	@Override
	public void updateRoundStatus(final long roundId, final RoundStatus status) {
		queryFactory.update(round)
			.set(round.status, status)
			.where(round.roundId.eq(roundId))
			.execute();
	}

	@Override
	public Round getUnassignedRound() {
		return queryFactory.select(round)
			.from(round)
			.where(
				round.status.eq(RoundStatus.PICK),
				round.workerId.isNull()
			)
			.fetchFirst();
	}
}
