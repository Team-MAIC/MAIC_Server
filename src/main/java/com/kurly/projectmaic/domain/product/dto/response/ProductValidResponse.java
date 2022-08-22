package com.kurly.projectmaic.domain.product.dto.response;

public record ProductValidResponse(
	int result,
	ProductInfoResponse product,
	ProductInfoResponse compare
) {
}
