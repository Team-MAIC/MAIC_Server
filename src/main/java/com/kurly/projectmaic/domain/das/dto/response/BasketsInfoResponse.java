package com.kurly.projectmaic.domain.das.dto.response;

import java.util.List;

public record BasketsInfoResponse(
	List<ProductsColorResponse> colors,
	List<BasketInfoResponse> baskets
) {
}
