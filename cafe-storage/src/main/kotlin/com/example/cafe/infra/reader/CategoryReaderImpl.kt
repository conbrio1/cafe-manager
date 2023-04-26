package com.example.cafe.infra.reader

import com.example.cafe.domain.product.Category
import com.example.cafe.domain.product.CategoryReader
import com.example.cafe.infra.repository.CategoryRepository
import org.springframework.stereotype.Component

@Component
class CategoryReaderImpl(
    private val categoryRepository: CategoryRepository
) : CategoryReader {

    override fun getCategories(): List<Category> {
        return categoryRepository.findAll()
    }

    override fun getCategoryByName(categoryName: String): Category? {
        return categoryRepository.findByName(categoryName)
    }
}
