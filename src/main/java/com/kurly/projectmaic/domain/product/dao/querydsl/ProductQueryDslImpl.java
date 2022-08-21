package com.kurly.projectmaic.domain.product.dao.querydsl;

import static com.kurly.projectmaic.domain.product.domain.QProduct.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kurly.projectmaic.domain.product.dto.querydsl.ProductDto;
import com.kurly.projectmaic.domain.product.dto.querydsl.QProductDto;
import com.kurly.projectmaic.domain.product.dto.ValidProductsDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductQueryDslImpl implements ProductQueryDsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public ValidProductsDto getValidProductCount(
		List<Long> productIds) {
		List<ProductDto> products = queryFactory.select(
				new QProductDto(
					product.productId,
					product.productName,
					product.weight,
					product.productThumbnail,
					product.price
				)
			)
			.from(product)
			.where(
				product.productId.in(productIds)
			)
			.fetch();

		long totalPrice = products.stream()
			.mapToLong(ProductDto::price)
			.reduce(Long::sum)
			.orElse(0);

		return new ValidProductsDto(
			products.size(),
			totalPrice,
			products
		);
	}
}
