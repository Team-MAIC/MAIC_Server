package com.kurly.projectmaic.domain.das.dao;

import java.util.Optional;

import com.kurly.projectmaic.domain.das.dao.querydsl.DasQueryDsl;
import com.kurly.projectmaic.domain.das.domain.Das;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DasRepository extends JpaRepository<Das, Long>, DasQueryDsl {
	Optional<Das> findByPassage(long passage);
}
