package com.example.cafe.presentation.dto.response

import com.example.cafe.application.dto.info.AuthInfo
import com.example.cafe.application.dto.info.UserInfo

object UserResponse {

    data class UserResponse(
        val id: Long,
        val phoneNumber: String,
        val role: String
    ) {
        companion object {
            fun of(userInfo: UserInfo.UserInfo) = UserResponse(
                id = userInfo.id,
                phoneNumber = userInfo.phoneNumber,
                role = userInfo.role
            )
        }
    }

    data class TokenResponse(
        val accessToken: String,
        val refreshToken: String
    ) {
        companion object {
            fun of(tokenInfo: AuthInfo.TokenInfo) = TokenResponse(
                accessToken = tokenInfo.accessToken,
                refreshToken = tokenInfo.refreshToken
            )
        }
    }
}
