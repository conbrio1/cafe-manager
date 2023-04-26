package com.example.cafe.presentation.dto.response

data class BaseResponseBody<T>(
    val meta: Meta,
    val data: T? = null
)

data class Meta(
    val code: Int,
    val message: String
)
