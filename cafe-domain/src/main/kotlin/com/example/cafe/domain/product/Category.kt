package com.example.cafe.domain.product

import com.example.cafe.domain.common.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

/**
 * 카테고리
 * - 카테고리는 여러 상품을 포함한다.
 * - 카테고리 간 상하관계는 존재하지 않는다.
 * - ex. 커피, 디저트, 음료
 */
@Entity
class Category private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false)
    var name: String
) : BaseEntity() {
    @OneToMany(mappedBy = "category")
    private val _products: MutableList<Product> = mutableListOf()
    val products: List<Product>
        get() = _products.toList()

    constructor(name: String) : this(
        id = 0L,
        name = name
    )
}
