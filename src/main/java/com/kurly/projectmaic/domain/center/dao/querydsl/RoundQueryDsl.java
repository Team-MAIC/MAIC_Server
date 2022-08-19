package com.kurly.projectmaic.domain.center.dao.querydsl;

import java.util.List;

import com.kurly.projectmaic.domain.center.dto.querydsl.RoundDto;

public interface RoundQueryDsl {
	List<RoundDto> findToDoRoundsByCenterId(final long centerId);
}
