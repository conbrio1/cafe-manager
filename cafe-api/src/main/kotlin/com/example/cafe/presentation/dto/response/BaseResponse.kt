package com.example.cafe.presentation.dto.response

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

class BaseResponse<T>(
    val status: HttpStatus,
    val body: BaseResponseBody<T>?,
    val headers: MultiValueMap<String, String>? = null
) : ResponseEntity<BaseResponseBody<T>>(body, headers, status) {
    companion object {
        private val defaultHeader = LinkedMultiValueMap<String, String>().apply {
            add("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        }

        fun <T> ok(data: T): BaseResponse<T> {
            return BaseResponse(
                status = HttpStatus.OK,
                body = BaseResponseBody(
                    meta = Meta(
                        code = HttpStatus.OK.value(),
                        message = HttpStatus.OK.reasonPhrase
                    ),
                    data = data
                ),
                headers = defaultHeader.clone()
            )
        }

        fun <T> created(data: T, location: String): BaseResponse<T> {
            return BaseResponse(
                status = HttpStatus.CREATED,
                body = BaseResponseBody(
                    meta = Meta(
                        code = HttpStatus.CREATED.value(),
                        message = HttpStatus.CREATED.reasonPhrase
                    ),
                    data = data
                ),
                headers = defaultHeader.clone().apply {
                    add("Location", location)
                }
            )
        }

        fun fail(status: HttpStatus, message: String): BaseResponse<String> {
            return BaseResponse(
                status = status,
                body = BaseResponseBody(
                    meta = Meta(
                        code = status.value(),
                        message = message
                    ),
                    data = null
                ),
                headers = defaultHeader.clone()
            )
        }

        fun noContent(): BaseResponse<Unit> {
            return BaseResponse(
                status = HttpStatus.NO_CONTENT,
                body = null,
                headers = null
            )
        }
    }
}
