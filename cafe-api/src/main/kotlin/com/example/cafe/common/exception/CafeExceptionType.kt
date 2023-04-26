package com.example.cafe.common.exception

import org.springframework.http.HttpStatus

enum class CafeExceptionType(
    val status: HttpStatus,
    val message: String
) {
    USER_DUPLICATION(
        status = HttpStatus.BAD_REQUEST,
        message = "이미 존재하는 유저입니다."
    ),
    ROLE_NOT_FOUND(
        status = HttpStatus.NOT_FOUND,
        message = "존재하지 않는 권한입니다."
    ),
    USER_NOT_FOUND(
        status = HttpStatus.NOT_FOUND,
        message = "존재하지 않는 유저입니다."
    ),
    PASSWORD_MISMATCH(
        status = HttpStatus.BAD_REQUEST,
        message = "비밀번호가 일치하지 않습니다."
    ),
    REFRESH_REQUEST_USING_ACCESS_TOKEN(
        status = HttpStatus.UNAUTHORIZED,
        message = "access token을 사용한 토큰 재발급 요청입니다. refresh token을 사용해주세요."
    ),
    CATEGORY_NOT_FOUND(
        status = HttpStatus.NOT_FOUND,
        message = "존재하지 않는 카테고리입니다."
    )
}
