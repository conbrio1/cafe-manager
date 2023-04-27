package com.example.cafe.presentation

import com.example.cafe.application.ProductService
import com.example.cafe.common.swagger.CreateProductSwaggerMeta
import com.example.cafe.common.swagger.DeleteProductSwaggerMeta
import com.example.cafe.common.swagger.GetProductSwaggerMeta
import com.example.cafe.common.swagger.GetProductsSwaggerMeta
import com.example.cafe.common.swagger.UpdateProductSwaggerMeta
import com.example.cafe.presentation.dto.request.ProductRequest
import com.example.cafe.presentation.dto.response.BaseResponse
import com.example.cafe.presentation.dto.response.CursorPaginationResponse
import com.example.cafe.presentation.dto.response.ProductResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Tag(name = "상품", description = "상품 관련 API")
@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService
) {

    @GetProductsSwaggerMeta
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

    @CreateProductSwaggerMeta
    @PostMapping
    fun createProduct(
        @Valid @RequestBody
        request: ProductRequest.ProductCreateRequest
    ): BaseResponse<ProductResponse.ProductResponse> {
        val productCreateCommand = request.toCommand()
        val productInfo = productService.createProduct(productCreateCommand)
        val productResponse = ProductResponse.ProductResponse.of(productInfo)

        return BaseResponse.created(productResponse, "/products/${productResponse.id}")
    }

    @UpdateProductSwaggerMeta
    @PatchMapping("/{productId}")
    fun updateProduct(
        @PathVariable("productId", required = true) productId: Long,
        @Valid @RequestBody
        request: ProductRequest.ProductUpdateRequest
    ): BaseResponse<Unit> {
        val productUpdateCommand = request.toCommand(productId)
        productService.updateProduct(productUpdateCommand)

        return BaseResponse.noContent()
    }

    @DeleteProductSwaggerMeta
    @DeleteMapping("/{productId}")
    fun deleteProduct(
        @PathVariable("productId", required = true) productId: Long
    ): BaseResponse<Unit> {
        productService.deleteProduct(productId)

        return BaseResponse.noContent()
    }

    @GetProductSwaggerMeta
    @GetMapping("/{productId}")
    fun getProduct(
        @PathVariable("productId", required = true) productId: Long
    ): BaseResponse<ProductResponse.ProductResponse> {
        val productInfo = productService.getProductDetail(productId)
        val productResponse = ProductResponse.ProductResponse.of(productInfo)

        return BaseResponse.ok(productResponse)
    }
}
