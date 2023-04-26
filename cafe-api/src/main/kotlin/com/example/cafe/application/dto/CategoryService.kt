package com.example.cafe.application.dto

import com.example.cafe.application.dto.info.CategoryInfo
import com.example.cafe.domain.product.CategoryReader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class CategoryService(
    private val categoryReader: CategoryReader
) {
    fun getCategories(): List<CategoryInfo.CategoryInfo> {
        return categoryReader.getCategories().map { CategoryInfo.CategoryInfo.of(it) }
    }
}
