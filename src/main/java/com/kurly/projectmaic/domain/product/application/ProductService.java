package com.kurly.projectmaic.domain.product.application;

import com.kurly.projectmaic.domain.center.exception.WorkerNotFoundException;
import com.kurly.projectmaic.domain.product.dao.ProductRepository;
import com.kurly.projectmaic.domain.product.domain.Product;
import com.kurly.projectmaic.domain.product.dto.response.ProductInfoResponse;
import com.kurly.projectmaic.domain.product.exception.ProductNotFoundException;
import com.kurly.projectmaic.global.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public ProductInfoResponse getProductInfo(String barcode) {
		Product product = productRepository.findByBarcode(barcode)
			.orElseThrow(() -> new ProductNotFoundException(ResponseCode.NOT_FOUND_PRODUCT,
				String.format("barcode : %s", barcode)));
		return new ProductInfoResponse(product.getProductId(), product.getProductName());
	}
}
