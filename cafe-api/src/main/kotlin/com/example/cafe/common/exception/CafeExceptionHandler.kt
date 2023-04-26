package com.example.cafe.common.exception

import com.example.cafe.presentation.dto.response.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class CafeExceptionHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(CafeRuntimeException::class)
    fun handleCafeRuntimeException(ex: CafeRuntimeException): BaseResponse<String> {
        logger.info(ex.message)

        return BaseResponse.fail(
            status = ex.type.status,
            message = ex.type.message
        )
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(ex: NoHandlerFoundException): BaseResponse<String> {
        logger.error(ex.stackTraceToString())

        return BaseResponse.fail(
            status = HttpStatus.NOT_FOUND,
            message = HttpStatus.NOT_FOUND.reasonPhrase
        )
    }

    @ExceptionHandler
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): BaseResponse<String> {
        logger.debug(ex.message)

        val message = ex.bindingResult.allErrors.mapNotNull { it.defaultMessage }.joinToString()
        return BaseResponse.fail(
            status = HttpStatus.BAD_REQUEST,
            message = message
        )
    }

    @ExceptionHandler
    fun handleConstraintViolationException(ex: ConstraintViolationException): BaseResponse<String> {
        logger.debug(ex.message)

        val message = ex.constraintViolations.joinToString { it.message }
        return BaseResponse.fail(
            status = HttpStatus.BAD_REQUEST,
            message = message
        )
    }
}
