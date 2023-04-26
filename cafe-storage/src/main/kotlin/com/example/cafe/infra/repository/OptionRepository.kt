package com.example.cafe.infra.repository

import com.example.cafe.domain.product.Option
import com.example.cafe.domain.product.OptionGroup
import org.springframework.data.jpa.repository.JpaRepository

interface OptionRepository : JpaRepository<Option, Long> {

    fun findByNameAndOptionGroup(name: String, optionGroup: OptionGroup): Option?

    fun findByOptionGroup(optionGroup: OptionGroup): List<Option>
}
