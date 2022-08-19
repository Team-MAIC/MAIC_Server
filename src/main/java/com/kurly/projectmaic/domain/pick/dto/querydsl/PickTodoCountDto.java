package com.kurly.projectmaic.domain.pick.dto.querydsl;

import com.querydsl.core.annotations.QueryProjection;

public record PickTodoCountDto(long roundId, long count) {

    @QueryProjection
    public PickTodoCountDto {
    }
}
