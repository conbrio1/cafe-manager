package com.example.cafe.common.exception

import java.lang.RuntimeException

class CafeRuntimeException(
    val type: CafeExceptionType
) : RuntimeException(type.message)
