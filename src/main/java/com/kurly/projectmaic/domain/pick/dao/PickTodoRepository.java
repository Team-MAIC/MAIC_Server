package com.kurly.projectmaic.domain.pick.dao;

import com.kurly.projectmaic.domain.das.domain.DasTodo;
import com.kurly.projectmaic.domain.pick.dao.querydsl.PickTodoQueryDsl;
import com.kurly.projectmaic.domain.pick.domain.PickTodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PickTodoRepository extends JpaRepository<PickTodo, Long>, PickTodoQueryDsl {
	Optional<PickTodo> findByRoundIdAndProductId(Long roundId, Long productId);

}
