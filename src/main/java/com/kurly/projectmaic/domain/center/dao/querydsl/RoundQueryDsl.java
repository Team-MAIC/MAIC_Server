package com.kurly.projectmaic.domain.center.dao.querydsl;

import java.util.List;
import java.util.Optional;

import com.kurly.projectmaic.domain.center.domain.Round;
import com.kurly.projectmaic.domain.center.dto.querydsl.CurrentRoundDto;
import com.kurly.projectmaic.domain.center.dto.querydsl.RoundDto;
import com.kurly.projectmaic.domain.center.enumeration.RoundStatus;

public interface RoundQueryDsl {
	CurrentRoundDto getCurrentRoundId(final long centerId);
	Long createNewRound(final long centerId, final long centerRoundNumber);
	List<RoundDto> findToDoRoundsByCenterId(final long centerId);
	void updateRoundStatus(final long roundId, final RoundStatus status);
	Round getUnassignedRound(final long centerId);
	Optional<Round> getLastRoundByPassage(final long centerId, final int passage);
}
