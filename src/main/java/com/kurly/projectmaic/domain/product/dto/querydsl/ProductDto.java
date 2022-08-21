package com.kurly.projectmaic.domain.product.dto.querydsl;

import com.querydsl.core.annotations.QueryProjection;

public record ProductDto(
	long productId,
	String productName,
	double weight,
	String productThumbnail,
	long price
) {

	@QueryProjection
	public ProductDto {
	}
}
