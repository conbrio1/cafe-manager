package com.example.cafe.presentation.dto.request

import com.example.cafe.application.dto.command.UserCommand
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

object UserRequest {
    data class SignUpRequest(
        @field:NotBlank(message = "phoneNumber는 필수값입니다")
        @field:Pattern(
            regexp = "^(01[016789])([0-9]{3,4})([0-9]{4})$",
            message = "'-' 생략하여 입력해주십시오. 올바르지 않은 phoneNumber 형식입니다"
        )
        val phoneNumber: String,

        @field:NotBlank(message = "password는 필수값입니다")
        @field:Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*()-_=+]).{8,24}$",
            message = "password은 길이 8~24의 최소 1개 이상 영문, 숫자, 특수문자 조합으로 생성해야 합니다"
        )
        val password: String,

        @field:NotBlank(message = "role은 필수값입니다")
        @field:Pattern(
            regexp = "^(ROLE_EMPLOYEE|ROLE_MANAGER)$",
            message = "role은 ROLE_EMPLOYEE, ROLE_MANAGER 중 하나만 선택해주십시오"
        )
        val role: String
    ) {
        fun toCommand() = UserCommand.UserCreateCommand(
            phoneNumber = phoneNumber,
            password = password,
            role = role
        )
    }

    data class LoginRequest(
        @field:NotBlank(message = "phoneNumber는 필수값입니다")
        @field:Pattern(
            regexp = "^(01[016789])([0-9]{3,4})([0-9]{4})$",
            message = "'-' 생략하여 입력해주십시오. 올바르지 않은 phoneNumber 형식입니다"
        )
        val phoneNumber: String,

        @field:NotBlank(message = "password는 필수값입니다")
        val password: String
    ) {
        fun toCommand() = UserCommand.UserAuthCommand(
            phoneNumber = phoneNumber,
            password = password
        )
    }
}
