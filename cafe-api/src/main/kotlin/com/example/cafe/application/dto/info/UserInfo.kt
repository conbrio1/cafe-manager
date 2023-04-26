package com.example.cafe.application.dto.info

import com.example.cafe.domain.user.User

object UserInfo {

    data class UserInfo(
        val id: Long,
        val phoneNumber: String,
        val role: String
    ) {
        companion object {
            fun of(user: User) = UserInfo(
                id = user.id,
                phoneNumber = user.phoneNumber,
                role = user.userRoles.joinToString(", ") { it.role.name }
            )
        }
    }
}
