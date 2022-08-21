package com.kurly.projectmaic.domain.order.dao;

import com.kurly.projectmaic.domain.order.dao.querydsl.OrderInfoQueryDsl;
import com.kurly.projectmaic.domain.order.domain.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long>, OrderInfoQueryDsl {
	long countByRoundId(long roundId);
}
