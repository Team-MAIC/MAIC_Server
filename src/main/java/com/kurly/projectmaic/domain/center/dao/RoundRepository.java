package com.kurly.projectmaic.domain.center.dao;

import java.util.Optional;

import com.kurly.projectmaic.domain.center.dao.querydsl.RoundQueryDsl;
import com.kurly.projectmaic.domain.center.domain.Round;
import com.kurly.projectmaic.domain.center.enumeration.RoundStatus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundRepository extends JpaRepository<Round, Long>, RoundQueryDsl {
	Optional<Round> findByCenterIdAndPassageAndStatus(final long centerId, final int passage, final RoundStatus status);
}
