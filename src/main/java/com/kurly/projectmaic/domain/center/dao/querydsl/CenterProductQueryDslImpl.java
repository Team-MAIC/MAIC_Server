package com.kurly.projectmaic.domain.center.dao.querydsl;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CenterProductQueryDslImpl {

	private final JPAQueryFactory queryFactory;


}
