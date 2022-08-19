package com.kurly.projectmaic.domain.das.dao;

import com.kurly.projectmaic.domain.das.domain.Das;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DasRepository extends JpaRepository<Das, Long> {
}
