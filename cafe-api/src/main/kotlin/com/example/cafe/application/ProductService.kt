package com.example.cafe.application

import com.example.cafe.application.dto.info.ProductInfo
import com.example.cafe.common.exception.CafeExceptionType
import com.example.cafe.common.exception.CafeRuntimeException
import com.example.cafe.domain.product.CategoryReader
import com.example.cafe.domain.product.ProductReader
import com.example.cafe.domain.product.ProductStore
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ProductService(
    private val productReader: ProductReader,
    private val productStore: ProductStore,
    private val categoryReader: CategoryReader
) {

    fun getProducts(
        pageSize: Int,
        sortDirection: Sort.Direction?,
        nameQuery: String?,
        categoryName: String?,
        lastFindId: Long?
    ): Slice<ProductInfo.ProductInfo> {
        val categoryId = categoryName?.let { categoryReader.getCategoryByName(it)?.id }

        if (categoryName != null && categoryId == null) {
            throw CafeRuntimeException(CafeExceptionType.CATEGORY_NOT_FOUND)
        }

        val productDtoSlice = productReader.getProducts(pageSize, sortDirection, nameQuery, categoryId, lastFindId)
        return productDtoSlice.map { ProductInfo.ProductInfo.of(it) }
    }

    @Transactional
    fun createProduct() {
    }

    @Transactional
    fun updateProduct() {
    }

    @Transactional
    fun deleteProduct() {
    }

    fun getProductDetail() {
    }
}
