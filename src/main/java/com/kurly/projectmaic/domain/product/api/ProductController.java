package com.kurly.projectmaic.domain.product.api;

import com.kurly.projectmaic.domain.product.application.ProductService;
import com.kurly.projectmaic.domain.product.dto.response.ProductInfoResponse;
import com.kurly.projectmaic.domain.product.dto.response.ProductValidResponse;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping("/{productId}")
	public CustomResponseEntity<ProductInfoResponse> getProductInfo(
		@PathVariable final long productId){
		return CustomResponseEntity.success(productService.getProductInfo(productId));
	}

	@GetMapping("/barcode/{barcode}")
	public CustomResponseEntity<ProductInfoResponse> getProductInfoByBarcode(
		@PathVariable final String barcode){
		return CustomResponseEntity.success(productService.getProductInfoByBarcode(barcode));
	}

	@GetMapping("/valid/{productId}/{barcode}")
	public CustomResponseEntity<ProductValidResponse> validProduct(
		@PathVariable final long productId,
		@PathVariable final String barcode
	) {
		return CustomResponseEntity.success(productService.validProduct(productId, barcode));
	}
}
