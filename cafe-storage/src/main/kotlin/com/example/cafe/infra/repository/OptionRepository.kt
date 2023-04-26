package com.example.cafe.infra.repository

import com.example.cafe.domain.product.Option
import org.springframework.data.jpa.repository.JpaRepository

interface OptionRepository : JpaRepository<Option, Long>
