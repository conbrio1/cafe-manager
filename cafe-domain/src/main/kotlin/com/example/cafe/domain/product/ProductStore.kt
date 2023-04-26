package com.example.cafe.domain.product

interface ProductStore {
    fun store(product: Product): Product

    fun remove(product: Product)
}
