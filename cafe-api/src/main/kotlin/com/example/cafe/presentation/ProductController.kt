package com.example.cafe.presentation

import com.example.cafe.application.ProductService
import com.example.cafe.presentation.dto.response.BaseResponse
import com.example.cafe.presentation.dto.response.CursorPaginationResponse
import com.example.cafe.presentation.dto.response.ProductResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "상품", description = "상품 관련 API")
@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping
    fun getProducts(
        @RequestParam("pageSize", defaultValue = "10") pageSize: Int,
        @RequestParam("sortDirection", defaultValue = "DESC") sortDirection: Sort.Direction?,
        @RequestParam("query") nameQuery: String?,
        @RequestParam("categoryName") categoryName: String?,
        @RequestParam("lastFindId") lastFindId: Long?
    ): BaseResponse<CursorPaginationResponse<ProductResponse.ProductResponse>> {
        val productInfoSlice = productService.getProducts(pageSize, sortDirection, nameQuery, categoryName, lastFindId)
        val productResponseSlice = productInfoSlice.map { ProductResponse.ProductResponse.of(it) }

        return BaseResponse.ok(CursorPaginationResponse.of(productResponseSlice))
    }

//    @PostMapping("/")
//    fun createProduct(
//        @Valid @RequestBody request: ProductRequest.ProductCreateRequest
//    ) {
//
//        val productCreateCommand = request.toCommand()
//        val productInfo = productService.createProduct(productCreateCommand)
//
//    }
//
//    @PatchMapping("/{productId}")
//    fun updateProduct() {
//
//    }
//
//    @DeleteMapping("/{productId}")
//    fun deleteProduct() {
//
//    }
//
//    @GetMapping("/{productId}")
//    fun getProductDetail() {
//
//    }
}
