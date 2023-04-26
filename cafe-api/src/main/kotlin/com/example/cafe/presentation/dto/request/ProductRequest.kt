package com.example.cafe.presentation.dto.request

import com.example.cafe.application.dto.command.ProductCommand
import java.time.LocalDate
import javax.validation.constraints.Future
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

object ProductRequest {

    data class ProductCreateRequest(
        @field:NotBlank(message = "name은 필수값입니다")
        val name: String,

        @field:NotBlank(message = "categoryName은 필수값입니다")
        val categoryName: String,

        @field:Positive(message = "price는 0보다 커야 합니다")
        val price: Int,

        @field:Positive(message = "cost는 0보다 커야 합니다")
        val cost: Int,

        @field:NotBlank(message = "barcode는 필수값입니다")
        val barcode: String,

        @field:Future(message = "expirationDate는 현재 날짜보다 미래여야 합니다")
        val expirationDate: LocalDate,

        @field:NotBlank(message = "description은 필수값입니다")
        val description: String
    ) {
        fun toCommand() = ProductCommand.ProductCreateCommand(
            name = name,
            price = price,
            cost = cost,
            description = description,
            barcode = barcode,
            expirationDate = expirationDate,
            categoryName = categoryName
        )
    }
}
