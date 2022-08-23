package com.kurly.projectmaic.domain.das.dto.request;

import java.util.List;

public record BasketsMappingRequest(
	List<BasketMappingRequest> baskets
) {
}
