package com.kurly.projectmaic.domain.center.dao;

import com.kurly.projectmaic.domain.center.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findMessagesByWorkerIdAndIsVisibleOrderByCreatedAtDesc(final long workerId, final boolean isVisible);
}
