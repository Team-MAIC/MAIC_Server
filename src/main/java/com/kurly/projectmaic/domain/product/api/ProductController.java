package com.kurly.projectmaic.domain.product.api;

import com.kurly.projectmaic.domain.product.application.ProductService;
import com.kurly.projectmaic.domain.product.dto.response.ProductInfoResponse;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping("/{barcode}")
	public CustomResponseEntity<ProductInfoResponse> getProductInfo(
		@PathVariable final String barcode){
		return CustomResponseEntity.success(productService.getProductInfo(barcode));
	}
}
