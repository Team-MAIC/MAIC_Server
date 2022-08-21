package com.kurly.projectmaic.domain.order.dto;

import java.util.List;

public record PurchaseRequest(
	long consumerId,
	long centerId,
	List<ProductRequest> products
) {
}
