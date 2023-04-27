package com.example.cafe.presentation

import com.example.cafe.application.UserService
import com.example.cafe.common.swagger.SignUpSwaggerMeta
import com.example.cafe.presentation.dto.request.UserRequest
import com.example.cafe.presentation.dto.response.BaseResponse
import com.example.cafe.presentation.dto.response.UserResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Tag(name = "회원", description = "회원 관련 API")
@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @SignUpSwaggerMeta
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody @Valid
        request: UserRequest.SignUpRequest
    ): BaseResponse<UserResponse.UserResponse> {
        val userCreateCommand = request.toCommand()
        val userInfo = userService.createUser(userCreateCommand)
        val userResponse = UserResponse.UserResponse.of(userInfo)

        return BaseResponse.created(userResponse, "/users/${userInfo.id}")
    }
}
