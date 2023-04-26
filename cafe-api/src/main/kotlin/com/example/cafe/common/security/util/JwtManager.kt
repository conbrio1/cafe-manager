package com.example.cafe.common.security.util

import com.example.cafe.common.security.TokenType
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class JwtManager(
    private val jwtEncoder: JwtEncoder,
    @Value("\${spring.security.oauth2.resourceserver.jwt.access-token-expire-seconds}")
    private val accessTokenExpireSeconds: Long,
    @Value("\${spring.security.oauth2.resourceserver.jwt.refresh-token-expire-seconds}")
    private val refreshTokenExpireSeconds: Long
) {

    companion object {
        const val ISSUER = "cafe-manager"
    }

    fun generateAccessToken(authentication: Authentication): String {
        return generateJwt(authentication, TokenType.ACCESS, accessTokenExpireSeconds)
    }

    fun generateRefreshToken(authentication: Authentication): String {
        return generateJwt(authentication, TokenType.REFRESH, refreshTokenExpireSeconds)
    }

    private fun generateJwt(authentication: Authentication, tokenType: TokenType, expireSeconds: Long): String {
        val now = Instant.now()
        val header = JwsHeader
            .with(SignatureAlgorithm.RS256)
            .type("JWT")
            .build()
        val claimSet = JwtClaimsSet.builder()
            .subject(tokenType.name)
            .issuer(ISSUER)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expireSeconds))
            .claim("userId", authentication.name)
            .claim("roles", authentication.authorities.map { it.authority })
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claimSet)).tokenValue
    }
}
