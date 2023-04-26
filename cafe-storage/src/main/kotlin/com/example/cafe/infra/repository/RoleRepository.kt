package com.example.cafe.infra.repository

import com.example.cafe.domain.user.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Int> {
    fun findByName(name: String): List<Role>
}
