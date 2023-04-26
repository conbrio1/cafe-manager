package com.example.cafe.domain.product

import com.example.cafe.domain.common.BaseEntity
import java.time.LocalDate
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

/**
 * 상품
 * - 상품은 여러 옵션을 가질 수 있다.
 * - 상품은 하나의 카테고리에 속한다.
 */
@Entity
class Product private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false)
    var price: Int, // 가격
    @Column(nullable = false)
    var cost: Int, // 원가
    @Column(nullable = false)
    var description: String, // 설명
    @Column(nullable = false)
    var barcode: String, // 바코드
    @Column(nullable = false)
    var expirationDate: LocalDate, // 유통기한
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "category_id", nullable = false)
    var category: Category
) : BaseEntity() {
    @Column(nullable = false)
    var name: String = "" // 상품명
        private set

    @Column(nullable = false)
    var nameCosonant: String = "" // 상품명 자음
        private set

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _productOptions: MutableList<ProductOption> = mutableListOf()
    val productOptions: List<ProductOption>
        get() = _productOptions.toList()

    constructor(
        name: String,
        price: Int,
        cost: Int,
        description: String,
        barcode: String,
        expirationDate: LocalDate,
        category: Category
    ) : this(
        id = 0L,
        price = price,
        cost = cost,
        description = description,
        barcode = barcode,
        expirationDate = expirationDate,
        category = category
    ) {
        this.name = name
        this.nameCosonant = extractCosonant(name)
    }

    fun addProductOption(option: Option, optionPrice: Int) {
        val productOption = ProductOption(this, option, optionPrice)
        _productOptions.add(productOption)
    }

    fun removeProductOption(productOption: ProductOption) {
        _productOptions.remove(productOption)
    }

    fun changeName(name: String) {
        this.name = name
        this.nameCosonant = extractCosonant(name)
    }

    companion object {
        private fun extractCosonant(str: String): String {
            val cosonant = mutableListOf<Char>()
            for (i in str.indices) {
                val ch = str[i]
                if (ch in '가'..'힣') {
                    val cosonantIndex = (ch - '가') / 28 / 21
                    cosonant.add('ㄱ' + cosonantIndex)
                }
            }
            return cosonant.joinToString("")
        }
    }
}
