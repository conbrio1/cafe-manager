package com.example.cafe.common.swagger

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.security.SecurityRequirement

@Operation(
    summary = "상품 목록 조회",
    description = "상품 목록을 조회한다.",
    security = [
        SecurityRequirement(name = "jwtScheme")
    ]
)
@Parameters(
    value = [
        Parameter(
            name = "pageSize",
            `in` = ParameterIn.QUERY,
            description = "요청 페이지 사이즈. 기본값은 10"
        ),
        Parameter(
            name = "sortDirection",
            `in` = ParameterIn.QUERY,
            description = "정렬 방향. 기본값은 DESC"
        ),
        Parameter(
            name = "query",
            `in` = ParameterIn.QUERY,
            description = "상품명 검색어. 상품명에 검색어가 포함된 상품을 조회하며, 초성 검색도 지원한다." +
                "검색어가 없을 경우 전체 상품을 조회한다."
        ),
        Parameter(
            name = "categoryName",
            `in` = ParameterIn.QUERY,
            description = "카테고리명"
        ),
        Parameter(
            name = "lastFindId",
            `in` = ParameterIn.QUERY,
            description = "마지막 조회 상품 ID"
        )
    ]
)
annotation class GetProductsSwaggerMeta

@Operation(
    summary = "상품 생성",
    description = "상품을 생성한다.",
    security = [
        SecurityRequirement(name = "jwtScheme")
    ]
)
annotation class CreateProductSwaggerMeta

@Operation(
    summary = "상품 목록 조회",
    description = "상품 목록을 조회한다.",
    security = [
        SecurityRequirement(name = "jwtScheme")
    ]
)
@Parameters(
    value = [
        Parameter(
            name = "productId",
            `in` = ParameterIn.PATH,
            description = "상품 ID"
        )
    ]
)
annotation class UpdateProductSwaggerMeta

@Operation(
    summary = "상품 삭제",
    description = "상품을 삭제한다.",
    security = [
        SecurityRequirement(name = "jwtScheme")
    ]
)
annotation class DeleteProductSwaggerMeta

@Operation(
    summary = "상품 단건 조회",
    description = "상품 단건을 조회한다.",
    security = [
        SecurityRequirement(name = "jwtScheme")
    ]
)
@Parameters(
    value = [
        Parameter(
            name = "productId",
            `in` = ParameterIn.PATH,
            description = "상품 ID"
        )
    ]
)
annotation class GetProductSwaggerMeta

@Operation(
    summary = "카테고리 목록 조회",
    description = "카테고리 목록을 조회한다.",
    security = [
        SecurityRequirement(name = "jwtScheme")
    ]
)
annotation class GetCategoriesSwaggerMeta

@Operation(
    summary = "회원가입",
    description = "새로운 회원을 생성한다."
)
annotation class SignUpSwaggerMeta

@Operation(
    summary = "로그인",
    description = "로그인하며, 액세스 토큰과 리프레시 토큰을 발급한다."
)
annotation class LoginSwaggerMeta

@Operation(
    summary = "액세스 토큰 재발급",
    description = "액세스 토큰과 리프레시 토큰을 재발급한다.",
    security = [
        SecurityRequirement(name = "jwtScheme")
    ]
)
annotation class RefreshTokenSwaggerMeta
