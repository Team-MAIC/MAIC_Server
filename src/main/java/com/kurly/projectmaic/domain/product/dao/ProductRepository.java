package com.kurly.projectmaic.domain.product.dao;

import com.kurly.projectmaic.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
