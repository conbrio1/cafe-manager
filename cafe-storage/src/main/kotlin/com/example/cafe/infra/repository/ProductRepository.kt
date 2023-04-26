package com.example.cafe.infra.repository

import com.example.cafe.domain.product.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>, ProductRepositoryCustom
