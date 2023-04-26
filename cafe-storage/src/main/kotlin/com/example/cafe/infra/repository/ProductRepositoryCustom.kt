package com.example.cafe.infra.repository

import com.example.cafe.domain.product.dto.ProductDto
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort

interface ProductRepositoryCustom {

    fun findProducts(
        pageSize: Int,
        sortDirection: Sort.Direction? = Sort.Direction.ASC,
        nameQuery: String?,
        categoryId: Long?,
        lastFindId: Long?
    ): Slice<ProductDto>
}
