package com.example.cafe.presentation.dto.request

import com.example.cafe.application.dto.command.ProductCommand
import java.time.LocalDate
import javax.validation.constraints.Future
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

object ProductRequest {

    data class ProductCreateRequest(
        @field:NotBlank(message = "name은 필수값입니다")
        val name: String,

        @field:NotBlank(message = "categoryName은 필수값입니다")
        val categoryName: String,

        @field:Min(0, message = "price는 0 이상이어야 합니다")
        val price: Int,

        @field:Min(0, message = "cost는 0 이상이어야 합니다")
        val cost: Int,

        @field:NotBlank(message = "barcode는 필수값입니다")
        val barcode: String,

        @field:Future(message = "expirationDate는 현재 날짜보다 미래여야 합니다")
        val expirationDate: LocalDate,

        @field:NotBlank(message = "description은 필수값입니다")
        val description: String,

        @field:Size(min = 1, message = "productOptions는 최소 1개 이상이어야 합니다")
        val productOptions: List<ProductOptionCreateRequest>
    ) {
        fun toCommand() = ProductCommand.ProductCreateCommand(
            name = name,
            price = price,
            cost = cost,
            description = description,
            barcode = barcode,
            expirationDate = expirationDate,
            categoryName = categoryName,
            productOptions = productOptions.map { it.toCommand() }
        )
    }

    data class ProductUpdateRequest(
        val name: String?,

        val categoryName: String?,

        @field:Min(0, message = "price는 0 이상이어야 합니다")
        val price: Int?,

        @field:Min(0, message = "cost는 0 이상이어야 합니다")
        val cost: Int?,

        val barcode: String?,

        @field:Future(message = "expirationDate는 현재 날짜보다 미래여야 합니다")
        val expirationDate: LocalDate?,

        val description: String?,

        val productOptions: List<ProductOptionCreateRequest>?
    ) {
        fun toCommand(id: Long) = ProductCommand.ProductUpdateCommand(
            id = id,
            name = name,
            price = price,
            cost = cost,
            description = description,
            barcode = barcode,
            expirationDate = expirationDate,
            categoryName = categoryName,
            productOptions = productOptions?.map { it.toCommand() }
        )
    }

    data class ProductOptionCreateRequest(
        @field:NotBlank(message = "groupName은 필수값입니다")
        val groupName: String,

        @field:NotBlank(message = "optionName은 필수값입니다")
        val optionName: String,

        @field:Min(0, message = "optionPrice는 0 이상이어야 합니다")
        val optionPrice: Int
    ) {
        fun toCommand() = ProductCommand.ProductOptionCreateCommand(
            groupName = groupName,
            optionName = optionName,
            optionPrice = optionPrice
        )
    }
}
