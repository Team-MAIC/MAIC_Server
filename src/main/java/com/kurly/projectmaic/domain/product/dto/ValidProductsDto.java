package com.kurly.projectmaic.domain.product.dto;

import java.util.List;

import com.kurly.projectmaic.domain.product.dto.querydsl.ProductDto;

public record ValidProductsDto(
	long count,
	long totalPrice,
	List<ProductDto> products
) {
}
