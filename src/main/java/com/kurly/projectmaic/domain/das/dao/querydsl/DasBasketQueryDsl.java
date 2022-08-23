package com.kurly.projectmaic.domain.das.dao.querydsl;

import com.kurly.projectmaic.domain.das.domain.DasBasket;

import java.util.List;

public interface DasBasketQueryDsl {

	void bulkSave(List<Long> orderIds);
	List<DasBasket> findAllByOrderInfoId(final List<Long> orderInfoId);
}
