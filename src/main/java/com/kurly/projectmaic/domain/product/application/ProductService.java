package com.kurly.projectmaic.domain.product.application;

import java.util.Objects;

import com.kurly.projectmaic.domain.center.exception.WorkerNotFoundException;
import com.kurly.projectmaic.domain.product.dao.ProductRepository;
import com.kurly.projectmaic.domain.product.domain.Product;
import com.kurly.projectmaic.domain.product.dto.response.ProductInfoResponse;
import com.kurly.projectmaic.domain.product.dto.response.ProductValidResponse;
import com.kurly.projectmaic.domain.product.exception.ProductNotFoundException;
import com.kurly.projectmaic.global.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public ProductInfoResponse getProductInfo(final long productId) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException(ResponseCode.NOT_FOUND_PRODUCT,
				String.format("productId : %s", productId)));
		return new ProductInfoResponse(product.getProductId(), product.getProductName(), product.getProductThumbnail());
	}

	public ProductInfoResponse getProductInfoByBarcode(final String barcode) {
		Product product = productRepository.findByBarcode(barcode)
			.orElseThrow(() -> new ProductNotFoundException(ResponseCode.NOT_FOUND_PRODUCT,
				String.format("barcode : %s", barcode)));
		return new ProductInfoResponse(product.getProductId(), product.getProductName(), product.getProductThumbnail());
	}

	public ProductValidResponse validProduct(final long productId, final String barcode) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException(ResponseCode.NOT_FOUND_PRODUCT,
				String.format("productId : %s", productId)));

		Product compareProduct = productRepository.findByBarcode(barcode)
			.orElseThrow(() -> new ProductNotFoundException(ResponseCode.NOT_FOUND_PRODUCT,
				String.format("barcode : %s", barcode)));

		return new ProductValidResponse(
			(Objects.equals(product.getProductId(), compareProduct.getProductId())) ? ResponseCode.OK.getCode() : ResponseCode.FAIL.getCode(),
			new ProductInfoResponse(product.getProductId(), product.getProductName(), product.getProductThumbnail()),
			new ProductInfoResponse(compareProduct.getProductId(), compareProduct.getProductName(), compareProduct.getProductThumbnail())
		);
	}
}
