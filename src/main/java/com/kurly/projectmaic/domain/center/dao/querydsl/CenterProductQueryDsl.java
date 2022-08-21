package com.kurly.projectmaic.domain.center.dao.querydsl;

import java.util.List;

import com.kurly.projectmaic.domain.center.dto.querydsl.CenterProductDto;

public interface CenterProductQueryDsl {

	List<CenterProductDto> getCenterProducts(final long centerId, final List<Long> productIds);
}
