package com.example.cafe.application.dto.info

object AuthInfo {

    data class TokenInfo(
        val accessToken: String,
        val refreshToken: String
    )
}
