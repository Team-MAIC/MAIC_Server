package com.kurly.projectmaic.domain.product.dao.querydsl;

import java.util.List;

import com.kurly.projectmaic.domain.product.dto.ValidProductsDto;

public interface ProductQueryDsl {

	ValidProductsDto getValidProductCount(final List<Long> productIds);
}
