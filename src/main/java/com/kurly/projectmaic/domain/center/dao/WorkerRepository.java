package com.kurly.projectmaic.domain.center.dao;

import com.kurly.projectmaic.domain.center.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}
