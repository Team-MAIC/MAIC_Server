package com.kurly.projectmaic.domain.center.dao.querydsl;

import java.util.List;

import com.kurly.projectmaic.domain.center.dto.querydsl.RoundDto;
import com.kurly.projectmaic.domain.center.enumeration.RoundStatus;

public interface RoundQueryDsl {
	List<RoundDto> findToDoRoundsByCenterId(final long centerId);
	void updateRoundStatus(final long roundId, final RoundStatus status);
}
