package com.kurly.projectmaic.domain.das.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kurly.projectmaic.domain.das.dao.querydsl.DasBasketQueryDsl;
import com.kurly.projectmaic.domain.das.domain.DasBasket;

public interface DasBasketReopository extends JpaRepository<DasBasket, Long>, DasBasketQueryDsl {
}
