package com.example.cafe.domain.product

import com.example.cafe.domain.common.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

/**
 * 상품 옵션
 * - 상품에서 옵션을 선택할 수 있다.
 * - 관계 테이블을 형성한다.
 */
@Entity
class ProductOption private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    val product: Product,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    val option: Option,
    @Column(nullable = false)
    var optionPrice: Int // 해당 옵션의 추가 가격
) : BaseEntity() {

    constructor(product: Product, option: Option, optionPrice: Int) : this(
        id = 0L,
        product = product,
        option = option,
        optionPrice = optionPrice
    )
}
