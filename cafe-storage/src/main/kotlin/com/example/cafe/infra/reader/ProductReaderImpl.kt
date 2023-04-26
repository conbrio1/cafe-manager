package com.example.cafe.infra.reader

import com.example.cafe.domain.product.Product
import com.example.cafe.domain.product.ProductReader
import com.example.cafe.domain.product.dto.ProductDto
import com.example.cafe.infra.repository.ProductRepository
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class ProductReaderImpl(
    private val productRepository: ProductRepository
) : ProductReader {
    override fun getProducts(
        pageSize: Int,
        sortDirection: Sort.Direction?,
        nameQuery: String?,
        categoryId: Long?,
        lastFindId: Long?
    ): Slice<ProductDto> {
        return productRepository.findProducts(pageSize, sortDirection, nameQuery, categoryId, lastFindId)
    }

    override fun getProductByName(name: String): Product? {
        return productRepository.findByName(name)
    }

    override fun getProductById(id: Long): Product? {
        return productRepository.findById(id).orElse(null)
    }
}
