package com.example.cafe.domain.product

import com.example.cafe.domain.common.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * 옵션 그룹
 * - 하나의 옵션 그룹에 여러 옵션들이 속하며, 그 중 하나를 선택할 수 있다.
 * - ex. 사이즈(small, large), 시럽(o, x), 샷 추가(1샷, 2샷), ice/hot 등
 */
@Entity
class OptionGroup private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    @Column(nullable = false, length = 50)
    var name: String
) : BaseEntity() {

    constructor(name: String) : this(
        id = 0,
        name = name
    )
}
