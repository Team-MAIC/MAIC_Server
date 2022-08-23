package com.kurly.projectmaic.domain.pick.dao.querydsl;

import java.util.List;

import com.kurly.projectmaic.domain.center.dto.querydsl.CenterProductDto;
import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.kurly.projectmaic.domain.order.dto.querydsl.OrderProductByRoundIdDto;
import com.kurly.projectmaic.domain.pick.dto.querydsl.PickTodoCountDto;
import com.kurly.projectmaic.domain.pick.dto.querydsl.PickTodoDto;
import com.kurly.projectmaic.domain.pick.exception.PickTodoFilterType;
import com.kurly.projectmaic.domain.product.dto.querydsl.ProductDto;

public interface PickTodoQueryDsl {

	PickTodoCountDto getTodoCountByCurrentRound(final long roundId);
	List<PickTodoCountDto> getTodoCountByRounds(final List<Long> centerIds, final CenterProductArea area);
	List<PickTodoDto> getPickTodos(final long roundId, final CenterProductArea area, final long workerId,
		final PickTodoFilterType filterType);
	void bulkSave(
		final long roundId,
		final List<OrderProductByRoundIdDto> orderProducts,
		final List<ProductDto> productDtos,
		final List<CenterProductDto> centerProductDtos);

    Long getPickWorkerId(long roundId, long productId);
}
