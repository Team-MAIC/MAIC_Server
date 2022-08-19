package com.kurly.projectmaic.domain.pick.dao;

import com.kurly.projectmaic.domain.pick.domain.PickTodo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickTodoRepository extends JpaRepository<PickTodo, Long> {
}
