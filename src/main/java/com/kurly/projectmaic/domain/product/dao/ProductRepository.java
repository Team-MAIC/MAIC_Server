package com.kurly.projectmaic.domain.product.dao;

import com.kurly.projectmaic.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findByBarcode(String barcode);
}
