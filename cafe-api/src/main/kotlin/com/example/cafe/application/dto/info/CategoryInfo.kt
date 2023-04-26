package com.example.cafe.application.dto.info

import com.example.cafe.domain.product.Category

object CategoryInfo {
    data class CategoryInfo(
        val name: String
    ) {
        companion object {
            fun of(category: Category) = CategoryInfo(
                name = category.name
            )
        }
    }
}
