package com.example.cafe.infra.repository

import com.example.cafe.domain.product.OptionGroup
import org.springframework.data.jpa.repository.JpaRepository

interface OptionGroupRepository : JpaRepository<OptionGroup, Int> {

    fun findByName(name: String): OptionGroup?
}
