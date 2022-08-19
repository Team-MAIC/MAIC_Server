package com.kurly.projectmaic.domain.pick.dao.querydsl;

import java.util.List;

import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.kurly.projectmaic.domain.pick.dto.querydsl.PickTodoCountDto;

public interface PickTodoQueryDsl {

	List<PickTodoCountDto> getTodoCountByCurrentRound(final List<Long> centerIds, final CenterProductArea area);
}