package com.example.cafe.presentation.dto.response

import com.example.cafe.application.dto.info.CategoryInfo

object CategoryResponse {
    data class CategoryResponse(
        val name: String
    ) {
        companion object {
            fun of(categoryInfo: CategoryInfo.CategoryInfo) = CategoryResponse(
                name = categoryInfo.name
            )
        }
    }
}
