package com.example.cafe.presentation.dto.response

import com.example.cafe.application.dto.info.ProductInfo
import java.time.LocalDate

object ProductResponse {
    data class ProductResponse(
        val id: Long,
        val name: String,
        val price: Int,
        val cost: Int,
        val description: String,
        val barcode: String,
        val expirationDate: LocalDate,
        val categoryName: String,
        val productOptions: List<ProductOptionResponse>
    ) {
        companion object {
            fun of(productInfo: ProductInfo.ProductInfo) = ProductResponse(
                id = productInfo.id,
                name = productInfo.name,
                price = productInfo.price,
                cost = productInfo.cost,
                description = productInfo.description,
                barcode = productInfo.barcode,
                expirationDate = productInfo.expirationDate,
                categoryName = productInfo.categoryName,
                productOptions = productInfo.productOptions.map { ProductOptionResponse.of(it) }
            )
        }
    }

    data class ProductOptionResponse(
        val group: String,
        val name: String,
        val optionPrice: Int
    ) {
        companion object {
            fun of(productOptionInfo: ProductInfo.ProductOptionInfo) = ProductOptionResponse(
                group = productOptionInfo.groupName,
                name = productOptionInfo.name,
                optionPrice = productOptionInfo.optionPrice
            )
        }
    }
}
