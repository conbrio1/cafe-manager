package com.example.cafe.presentation

import com.example.cafe.application.dto.CategoryService
import com.example.cafe.presentation.dto.response.BaseResponse
import com.example.cafe.presentation.dto.response.CategoryResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "상품 카테고리", description = "상품 카테고리 관련 API")
@RestController
@RequestMapping("/categories")
class CategoryController(
    private val categoryService: CategoryService
) {

    @GetMapping
    fun getCategories(): BaseResponse<List<CategoryResponse.CategoryResponse>> {
        val categoryInfos = categoryService.getCategories()
        val categoryResponse = categoryInfos.map { CategoryResponse.CategoryResponse.of(it) }

        return BaseResponse.ok(categoryResponse)
    }
}
