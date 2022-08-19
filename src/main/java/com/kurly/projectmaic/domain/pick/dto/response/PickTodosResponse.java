package com.kurly.projectmaic.domain.pick.dto.response;

import java.util.List;

public record PickTodosResponse(
	List<PickTodoResponse> todos
) {
}
