package com.example.cafe.infra.repository

import com.example.cafe.domain.product.Product
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
import com.querydsl.jpa.impl.JPAQuery
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
        // join으로 인해 limit 적용이 제대로 안되므로, id를 기준으로 조회하기 위해 id의 범위를 우선 조회
        val idMinMax = getIdMinMaxToFind(pageSize, nameQuery, categoryId, sortDirection, lastFindId)
        // product 조회를 위한 query 생성
        val productSearchQuery =
            createQuery(idMinMax.first, idMinMax.second, sortDirection, nameQuery, categoryId)
        // product 조회 query 실행
        val productDtoList = productSearchQuery.fetchProduct()

        val hasNext = if (productDtoList.size > pageSize) {
            productDtoList.removeLast()
            true
        } else {
            false
        }

        return SliceImpl(productDtoList, Pageable.unpaged(), hasNext)
    }

    private fun getIdMinMaxToFind(
        pageSize: Int,
        nameQuery: String?,
        categoryId: Long?,
        sortDirection: Sort.Direction?,
        lastFindId: Long?
    ): Pair<Long, Long> {
        val idRange = jpaQueryFactory.select(product.id)
            .from(product)
            .innerJoin(category).on(category.id.eq(product.category.id))
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
            .fetch()

        return if (sortDirection == Sort.Direction.ASC) {
            val min = idRange.first()
            val max = idRange.last()

            Pair(min, max)
        } else {
            val min = idRange.last()
            val max = idRange.first()

            Pair(min, max)
        }
    }

    private fun createQuery(
        idMin: Long,
        idMax: Long,
        sortDirection: Sort.Direction?,
        nameQuery: String?,
        categoryId: Long?
    ): JPAQuery<Product> {
        return jpaQueryFactory
            .selectFrom(product)
            .innerJoin(category).on(category.id.eq(product.category.id))
            .innerJoin(productOption).on(product.id.eq(productOption.product.id))
            .innerJoin(option).on(option.id.eq(productOption.option.id))
            .innerJoin(optionGroup).on(optionGroup.id.eq(option.optionGroup.id))
            .where(
                productIdGoe(idMin),
                productIdLoe(idMax),
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
    }

    private fun JPAQuery<Product>.fetchProduct(): MutableList<ProductDto> {
        return this.transform(
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
    }

    private fun productIdGoe(minId: Long?) =
        if (minId != null) product.id.goe(minId) else null

    private fun productIdLoe(maxId: Long?) =
        if (maxId != null) product.id.loe(maxId) else null

    private fun productIdGt(lastFindId: Long?) =
        if (lastFindId != null) product.id.gt(lastFindId) else null

    private fun productIdLt(lastFindId: Long?) =
        if (lastFindId != null) product.id.lt(lastFindId) else null

    private fun categoryIdEq(categoryId: Long?) =
        if (categoryId != null) category.id.eq(categoryId) else null

    // 자음 초성 및 이름 검색 조건
    private fun productNameLike(nameQuery: String?): BooleanExpression? {
        if (nameQuery.isNullOrBlank()) {
            return null
        }

        return product.name.like("%$nameQuery%")
            .or(product.nameCosonant.like("%$nameQuery%"))
    }
}
