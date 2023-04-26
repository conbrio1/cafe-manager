package com.example.cafe.infra.repository

import com.example.cafe.domain.product.QCategory.category
import com.example.cafe.domain.product.QOption.option
import com.example.cafe.domain.product.QOptionGroup.optionGroup
import com.example.cafe.domain.product.QProduct.product
import com.example.cafe.domain.product.QProductOption.productOption
import com.example.cafe.domain.product.dto.ProductDto
import com.example.cafe.domain.product.dto.QProductDto
import com.example.cafe.domain.product.dto.QProductOptionDto
import com.querydsl.core.group.GroupBy
import com.querydsl.core.group.GroupBy.list
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.data.domain.Sort

class ProductRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : ProductRepositoryCustom {
    override fun findProducts(
        pageSize: Int,
        sortDirection: Sort.Direction?,
        nameQuery: String?,
        categoryId: Long?,
        lastFindId: Long?
    ): Slice<ProductDto> {
        val productDtoList = jpaQueryFactory
            .selectFrom(product)
            .innerJoin(category).on(category.id.eq(product.category.id))
            .innerJoin(productOption).on(product.id.eq(productOption.product.id))
            .innerJoin(option).on(option.id.eq(productOption.option.id))
            .innerJoin(optionGroup).on(optionGroup.id.eq(option.optionGroup.id))
            .where(
                if (sortDirection == Sort.Direction.ASC) {
                    productIdGt(lastFindId)
                } else {
                    productIdLt(lastFindId)
                },
                productNameLike(nameQuery),
                categoryIdEq(categoryId)
            )
            .orderBy(
                if (sortDirection == Sort.Direction.ASC) {
                    product.id.asc()
                } else {
                    product.id.desc()
                }
            )
            .limit(pageSize.toLong() + 1)
            .transform(
                GroupBy.groupBy(product.id).list(
                    QProductDto(
                        product.id,
                        product.name,
                        product.price,
                        product.cost,
                        product.description,
                        product.barcode,
                        product.expirationDate,
                        category,
                        list(
                            QProductOptionDto(
                                option.name,
                                optionGroup.name,
                                productOption.optionPrice
                            )
                        )
                    )
                )
            )

        val hasNext = if (productDtoList.size > pageSize) {
            productDtoList.removeLast()
            true
        } else {
            false
        }

        return SliceImpl(productDtoList, Pageable.unpaged(), hasNext)
    }

    private fun productIdGt(lastFindId: Long?) =
        if (lastFindId != null) product.id.gt(lastFindId) else null

    private fun productIdLt(lastFindId: Long?) =
        if (lastFindId != null) product.id.lt(lastFindId) else null

    private fun categoryIdEq(categoryId: Long?) =
        if (categoryId != null) category.id.eq(categoryId) else null

    private fun productNameLike(nameQuery: String?): BooleanExpression? {
        if (nameQuery.isNullOrBlank()) {
            return null
        }

        return product.name.like("%$nameQuery%")
            .or(product.nameCosonant.like("%$nameQuery%"))
    }
}
