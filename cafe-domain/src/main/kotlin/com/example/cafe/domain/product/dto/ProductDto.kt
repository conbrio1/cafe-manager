package com.example.cafe.domain.product.dto

import com.example.cafe.domain.product.Category
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDate

class ProductDto @QueryProjection constructor(
    val id: Long,
    val name: String,
    val price: Int,
    val cost: Int,
    val description: String,
    val barcode: String,
    val expirationDate: LocalDate,
    val category: Category,
    val productOptions: List<ProductOptionDto>
)

class ProductOptionDto @QueryProjection constructor(
    val optionGroupName: String,
    val optionName: String,
    val optionPrice: Int
)
