package com.example.cafe.domain.product

import com.example.cafe.domain.common.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * 옵션
 * - 옵션은 옵션 그룹에 속해있는 옵션들 중 하나이다.
 * - ex. 옵션 그룹: 사이즈, 옵션: small, large
 * - ex. 옵션 그룹: 시럽, 옵션: o, x
 * - ex. 옵션 그룹: 샷 추가, 옵션: 1샷, 2샷
 */
@Entity
@Table(name = "options")
class Option private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false)
    var name: String,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id", nullable = false)
    var optionGroup: OptionGroup
) : BaseEntity() {

    constructor(name: String, optionGroup: OptionGroup) : this(
        id = 0L,
        name = name,
        optionGroup = optionGroup
    )
}
