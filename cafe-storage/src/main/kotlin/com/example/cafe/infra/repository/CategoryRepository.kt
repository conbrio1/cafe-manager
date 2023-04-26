package com.example.cafe.infra.repository

import com.example.cafe.domain.product.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {

    fun findByName(name: String): Category?
}
