package com.example.cafe.infra.store

import com.example.cafe.domain.product.Category
import com.example.cafe.domain.product.CategoryStore
import com.example.cafe.infra.repository.CategoryRepository
import org.springframework.stereotype.Component

@Component
class CategoryStoreImpl(
    private val categoryRepository: CategoryRepository
) : CategoryStore {
    override fun store(category: Category): Category {
        return categoryRepository.save(category)
    }
}
