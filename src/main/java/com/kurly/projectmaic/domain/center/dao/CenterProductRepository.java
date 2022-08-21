package com.kurly.projectmaic.domain.center.dao;

import com.kurly.projectmaic.domain.center.dao.querydsl.CenterProductQueryDsl;
import com.kurly.projectmaic.domain.center.domain.CenterProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CenterProductRepository extends JpaRepository<CenterProduct, Long>, CenterProductQueryDsl {
}
