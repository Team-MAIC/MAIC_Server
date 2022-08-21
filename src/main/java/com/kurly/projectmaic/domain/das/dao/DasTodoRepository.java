package com.kurly.projectmaic.domain.das.dao;

import com.kurly.projectmaic.domain.das.dao.querydsl.DasTodoQueryDsl;
import com.kurly.projectmaic.domain.das.domain.DasTodo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DasTodoRepository extends JpaRepository<DasTodo, Long>, DasTodoQueryDsl {
}
