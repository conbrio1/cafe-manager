package com.example.cafe.presentation

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "상품 카테고리", description = "상품 카테고리 관련 API")
@RestController
@RequestMapping("/categories")
class CategoryController {

    @GetMapping("/")
    fun getCategories() {
    }
}
