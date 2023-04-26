package com.example.cafe.application.dto.command

import com.example.cafe.domain.user.User
import org.springframework.security.crypto.password.PasswordEncoder

object UserCommand {
    data class UserCreateCommand(
        val phoneNumber: String,
        val password: String,
        val role: String
    ) {
        fun toEntity(passwordEncoder: PasswordEncoder) = User(
            phoneNumber = phoneNumber,
            password = passwordEncoder.encode(password)
        )
    }

    data class UserAuthCommand(
        val phoneNumber: String,
        val password: String
    )
}
