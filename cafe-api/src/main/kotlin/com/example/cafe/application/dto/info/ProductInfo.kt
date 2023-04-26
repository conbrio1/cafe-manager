package com.example.cafe.application.dto.info

import com.example.cafe.domain.product.Product
import com.example.cafe.domain.product.ProductOption
import com.example.cafe.domain.product.dto.ProductDto
import com.example.cafe.domain.product.dto.ProductOptionDto
import java.time.LocalDate

object ProductInfo {
    data class ProductInfo(
        val id: Long,
        val name: String,
        val price: Int,
        val cost: Int,
        val description: String,
        val barcode: String,
        val expirationDate: LocalDate,
        val categoryName: String,
        val productOptions: List<ProductOptionInfo>
    ) {
        companion object {
            fun of(productDto: ProductDto) = ProductInfo(
                id = productDto.id,
                name = productDto.name,
                price = productDto.price,
                cost = productDto.cost,
                description = productDto.description,
                barcode = productDto.barcode,
                expirationDate = productDto.expirationDate,
                categoryName = productDto.category.name,
                productOptions = productDto.productOptions.map { ProductOptionInfo.of(it) }
            )

            fun of(product: Product) = ProductInfo(
                id = product.id,
                name = product.name,
                price = product.price,
                cost = product.cost,
                description = product.description,
                barcode = product.barcode,
                expirationDate = product.expirationDate,
                categoryName = product.category.name,
                productOptions = product.productOptions.map { ProductOptionInfo.of(it) }
            )
        }
    }

    data class ProductOptionInfo(
        val groupName: String,
        val name: String,
        val optionPrice: Int
    ) {
        companion object {
            fun of(productOptionDto: ProductOptionDto): ProductOptionInfo {
                return ProductOptionInfo(
                    groupName = productOptionDto.optionGroupName,
                    name = productOptionDto.optionName,
                    optionPrice = productOptionDto.optionPrice
                )
            }

            fun of(productOption: ProductOption) = ProductOptionInfo(
                groupName = productOption.option.optionGroup.name,
                name = productOption.option.name,
                optionPrice = productOption.optionPrice
            )
        }
    }
}
