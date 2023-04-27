package com.example.cafe.presentation

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "상품 옵션", description = "상품 옵션 관련 API")
@RestController
@RequestMapping("/options")
class OptionController {
    // TODO: 상품 옵션 관련 API 구현
}
