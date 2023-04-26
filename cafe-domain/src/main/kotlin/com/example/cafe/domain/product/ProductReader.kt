package com.example.cafe.domain.product

import com.example.cafe.domain.product.dto.ProductDto
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort

interface ProductReader {
    fun getProducts(
        pageSize: Int,
        sortDirection: Sort.Direction?,
        nameQuery: String?,
        categoryId: Long?,
        lastFindId: Long?
    ): Slice<ProductDto>

//    fun getProductDetail(id: Long): Product?
}
