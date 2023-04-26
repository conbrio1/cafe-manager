package com.example.cafe.domain.product

interface CategoryReader {

    fun getCategories(): List<Category>

    fun getCategoryByName(categoryName: String): Category?
}
