package com.example.cafe.infra.store

import com.example.cafe.domain.product.Product
import com.example.cafe.domain.product.ProductStore
import com.example.cafe.infra.repository.ProductRepository
import org.springframework.stereotype.Component

@Component
class ProductStoreImpl(
    private val productRepository: ProductRepository
) : ProductStore {

    override fun store(product: Product): Product {
        return productRepository.save(product)
    }

    override fun remove(product: Product) {
        productRepository.delete(product)
    }
}
