package com.example.cafe.application

import com.example.cafe.application.dto.command.UserCommand
import com.example.cafe.application.dto.info.AuthInfo
import com.example.cafe.common.exception.CafeExceptionType
import com.example.cafe.common.exception.CafeRuntimeException
import com.example.cafe.common.security.util.JwtManager
import com.example.cafe.domain.user.User
import com.example.cafe.domain.user.UserReader
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class AuthService(
    private val userReader: UserReader,
    private val passwordEncoder: PasswordEncoder,
    private val jwtManager: JwtManager
) {

    fun authenticateAndIssueToken(userAuthCommand: UserCommand.UserAuthCommand): AuthInfo.TokenInfo {
        val user = userReader.getUserByPhoneNumber(userAuthCommand.phoneNumber)
            ?: throw CafeRuntimeException(CafeExceptionType.USER_NOT_FOUND)

        authenticatePassword(userAuthCommand.password, user.password)

        return issueToken(user)
    }

    fun reIssueToken(authentication: Authentication): AuthInfo.TokenInfo {
        val accessToken = jwtManager.generateAccessToken(authentication)
        val refreshToken = jwtManager.generateRefreshToken(authentication)

        return AuthInfo.TokenInfo(accessToken, refreshToken)
    }

    private fun authenticatePassword(rawPassword: String, encodedPassword: String) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw CafeRuntimeException(CafeExceptionType.PASSWORD_MISMATCH)
        }
    }

    private fun issueToken(authenticatedUser: User): AuthInfo.TokenInfo {
        val userAuthorities = mutableListOf<GrantedAuthority>()
        authenticatedUser.userRoles.forEach { userAuthorities.add(SimpleGrantedAuthority(it.role.name)) }

        val authResult = UsernamePasswordAuthenticationToken(authenticatedUser.id, null, userAuthorities)

        val accessToken = jwtManager.generateAccessToken(authResult)
        val refreshToken = jwtManager.generateRefreshToken(authResult)

        return AuthInfo.TokenInfo(accessToken, refreshToken)
    }
}
