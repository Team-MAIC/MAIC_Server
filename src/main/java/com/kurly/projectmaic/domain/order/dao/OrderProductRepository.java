package com.kurly.projectmaic.domain.order.dao;

import com.kurly.projectmaic.domain.order.dao.querydsl.OrderProductQueryDsl;
import com.kurly.projectmaic.domain.order.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, OrderProductQueryDsl {
}
