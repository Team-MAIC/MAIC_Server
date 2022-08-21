package com.kurly.projectmaic.domain.center.dao.querydsl;

import static com.kurly.projectmaic.domain.center.domain.QCenterProduct.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kurly.projectmaic.domain.center.dto.querydsl.CenterProductDto;
import com.kurly.projectmaic.domain.center.dto.querydsl.QCenterProductDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CenterProductQueryDslImpl implements CenterProductQueryDsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<CenterProductDto> getCenterProducts(final long centerId, final List<Long> productIds) {
		return queryFactory.select(
				new QCenterProductDto(
					centerProduct.productId,
					centerProduct.area,
					centerProduct.line,
					centerProduct.location
				)
			)
			.from(centerProduct)
			.where(
				centerProduct.center.centerId.eq(centerId),
				centerProduct.productId.in(productIds)
			)
			.orderBy(centerProduct.productId.asc())
			.fetch();
	}
}
