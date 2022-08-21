package com.kurly.projectmaic.domain.order.dao;

import com.kurly.projectmaic.domain.order.domain.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
	long countByRoundId(long roundId);
}
