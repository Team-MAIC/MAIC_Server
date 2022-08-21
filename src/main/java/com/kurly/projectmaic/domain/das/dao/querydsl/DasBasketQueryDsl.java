package com.kurly.projectmaic.domain.das.dao.querydsl;

import java.util.List;

public interface DasBasketQueryDsl {

	void bulkSave(List<Long> orderIds);
}
