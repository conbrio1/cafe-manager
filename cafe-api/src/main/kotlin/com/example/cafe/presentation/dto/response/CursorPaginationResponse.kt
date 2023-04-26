package com.example.cafe.presentation.dto.response

import org.springframework.data.domain.Slice

data class CursorPaginationResponse<T>(
    val hasNext: Boolean,
    val pageSize: Int,
    val paginatedData: List<T>
) {
    companion object {
        fun <T> of(
            slice: Slice<T>
        ) = CursorPaginationResponse(
            hasNext = slice.hasNext(),
            pageSize = slice.size,
            paginatedData = slice.content
        )
    }
}
