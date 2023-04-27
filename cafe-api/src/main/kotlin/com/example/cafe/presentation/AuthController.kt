package com.example.cafe.presentation

import com.example.cafe.application.AuthService
import com.example.cafe.common.exception.CafeExceptionType
import com.example.cafe.common.exception.CafeRuntimeException
import com.example.cafe.common.security.TokenType
import com.example.cafe.common.swagger.LoginSwaggerMeta
import com.example.cafe.common.swagger.RefreshTokenSwaggerMeta
import com.example.cafe.presentation.dto.request.UserRequest
import com.example.cafe.presentation.dto.response.BaseResponse
import com.example.cafe.presentation.dto.response.UserResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Tag(name = "인증", description = "인증 관련 API")
@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @LoginSwaggerMeta
    @PostMapping("/login")
    fun login(
        @RequestBody @Valid
        request: UserRequest.LoginRequest
    ): BaseResponse<UserResponse.TokenResponse> {
        val tokenInfo = authService.authenticateAndIssueToken(request.toCommand())
        val tokenResponse = UserResponse.TokenResponse.of(tokenInfo)

        return BaseResponse.ok(tokenResponse)
    }

    @RefreshTokenSwaggerMeta
    @PostMapping("/refresh")
    fun refresh(
        authentication: Authentication
    ): BaseResponse<UserResponse.TokenResponse> {
        validateRefreshToken(authentication)

        val tokenInfo = authService.reIssueToken(authentication)
        val tokenResponse = UserResponse.TokenResponse.of(tokenInfo)

        return BaseResponse.ok(tokenResponse)
    }

    // TODO(redis 사용한 로그아웃-토큰 무효화 처리)

    private fun validateRefreshToken(authentication: Authentication) {
        val token = authentication.principal as Jwt
        if (token.subject != TokenType.REFRESH.name) {
            throw CafeRuntimeException(CafeExceptionType.REFRESH_REQUEST_USING_ACCESS_TOKEN)
        }
    }
}
