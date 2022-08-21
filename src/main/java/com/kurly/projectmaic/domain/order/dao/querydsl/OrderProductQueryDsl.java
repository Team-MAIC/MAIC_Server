package com.kurly.projectmaic.domain.order.dao.querydsl;

import java.util.List;

import com.kurly.projectmaic.domain.order.domain.OrderProduct;
import com.kurly.projectmaic.domain.order.dto.querydsl.OrderProductByRoundIdDto;

public interface OrderProductQueryDsl {

	void bulkSave(List<OrderProduct> products);
	List<OrderProductByRoundIdDto> getOrderProductsByRoundId(final long roundId);
}
