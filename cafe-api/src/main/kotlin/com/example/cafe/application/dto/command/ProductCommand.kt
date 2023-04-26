package com.example.cafe.application.dto.command

import com.example.cafe.domain.product.Category
import com.example.cafe.domain.product.Product
import java.time.LocalDate

object ProductCommand {
    data class ProductCreateCommand(
        val name: String,
        val price: Int,
        val cost: Int,
        val description: String,
        val barcode: String,
        val expirationDate: LocalDate,
        val categoryName: String
    ) {
        fun toEntity(category: Category): Product {
            return Product(
                name = name,
                price = price,
                cost = cost,
                description = description,
                barcode = barcode,
                expirationDate = expirationDate,
                category = category
            )
        }
    }
}
