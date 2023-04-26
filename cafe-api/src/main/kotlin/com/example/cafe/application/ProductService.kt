package com.example.cafe.application

import com.example.cafe.application.dto.command.ProductCommand
import com.example.cafe.application.dto.info.ProductInfo
import com.example.cafe.common.exception.CafeExceptionType
import com.example.cafe.common.exception.CafeRuntimeException
import com.example.cafe.domain.product.Category
import com.example.cafe.domain.product.CategoryReader
import com.example.cafe.domain.product.Option
import com.example.cafe.domain.product.OptionGroup
import com.example.cafe.domain.product.OptionReader
import com.example.cafe.domain.product.OptionStore
import com.example.cafe.domain.product.Product
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
    private val categoryReader: CategoryReader,
    private val optionReader: OptionReader,
    private val optionStore: OptionStore
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
    fun createProduct(
        productCreateCommand: ProductCommand.ProductCreateCommand
    ): ProductInfo.ProductInfo {
        // product 이름 중복 검사
        validateDuplicateProduct(productCreateCommand.name)

        // category 이름으로 category 조회. 없으면 새로 생성
        val category = categoryReader.getCategoryByName(productCreateCommand.categoryName)
            ?: Category(productCreateCommand.categoryName)

        val product = productCreateCommand.toEntity(category)
        val productOptions = productCreateCommand.productOptions

        // product option 추가
        renewProductOptions(product, productOptions)

        productStore.store(product)
        return ProductInfo.ProductInfo.of(product)
    }

    @Transactional
    fun updateProduct(
        productUpdateCommand: ProductCommand.ProductUpdateCommand
    ) {
        // product 존재 확인
        val product = productReader.getProductById(productUpdateCommand.id)
            ?: throw CafeRuntimeException(CafeExceptionType.PRODUCT_NOT_FOUND)

        if (productUpdateCommand.name != null) {
            validateDuplicateProduct(productUpdateCommand.name)
            product.changeName(productUpdateCommand.name)
        }

        if (productUpdateCommand.description != null) {
            product.description = productUpdateCommand.description
        }

        if (productUpdateCommand.price != null) {
            product.price = productUpdateCommand.price
        }

        if (productUpdateCommand.cost != null) {
            product.cost = productUpdateCommand.cost
        }

        if (productUpdateCommand.barcode != null) {
            product.barcode = productUpdateCommand.barcode
        }

        if (productUpdateCommand.expirationDate != null) {
            product.expirationDate = productUpdateCommand.expirationDate
        }

        if (productUpdateCommand.categoryName != null) {
            val category = categoryReader.getCategoryByName(productUpdateCommand.categoryName)
                ?: Category(productUpdateCommand.categoryName)
            product.category = category
        }

        if (productUpdateCommand.productOptions != null) {
            // 기존 option 모두 삭제
            product.removeAllProductOption()

            // 새로운 option 추가
            renewProductOptions(product, productUpdateCommand.productOptions)
        }

        productStore.store(product)
    }

    @Transactional
    fun deleteProduct(productId: Long) {
        val product = productReader.getProductById(productId)
            ?: throw CafeRuntimeException(CafeExceptionType.PRODUCT_NOT_FOUND)

        productStore.remove(product)
    }

    fun getProductDetail(productId: Long): ProductInfo.ProductInfo {
        val product = productReader.getProductById(productId)
            ?: throw CafeRuntimeException(CafeExceptionType.PRODUCT_NOT_FOUND)

        return ProductInfo.ProductInfo.of(product)
    }

    private fun validateDuplicateProduct(productName: String) {
        val foundProduct = productReader.getProductByName(productName)
        if (foundProduct != null) {
            throw CafeRuntimeException(CafeExceptionType.PRODUCT_NAME_DUPLICATION)
        }
    }

    private fun renewProductOptions(
        product: Product,
        productOptions: List<ProductCommand.ProductOptionCreateCommand>
    ) {
        // key: optionGroup 이름, value: (option 이름, option 가격) 리스트
        val optionMap = mutableMapOf<String, MutableList<Pair<String, Int>>>()
        productOptions.forEach {
            optionMap[it.groupName] = optionMap.getOrDefault(it.groupName, mutableListOf()).apply {
                add(Pair(it.optionName, it.optionPrice))
            }
        }

        optionMap.keys.forEach { optionGroupName ->
            val optionNamePriceList = optionMap[optionGroupName]!!

            // optionGroup 이름으로 optionGroup 조회. 없으면 새로 생성
            val optionGroup = optionReader.getOptionGroupByName(optionGroupName)
                ?: optionStore.storeOptionGroup(OptionGroup(optionGroupName))

            optionNamePriceList.forEach {
                val optionName = it.first
                val optionPrice = it.second

                // 같은 optionGroup에서 option 이름으로 option 조회. 없으면 새로 생성
                val option = optionReader.getOptionByNameAndGroup(optionName, optionGroup)
                    ?: optionStore.storeOption(Option(optionName, optionGroup))

                product.addProductOption(option, optionPrice)
            }
        }
    }
}
