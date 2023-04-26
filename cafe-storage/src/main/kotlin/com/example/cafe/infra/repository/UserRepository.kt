package com.example.cafe.infra.repository

import com.example.cafe.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByPhoneNumber(phoneNumber: String): User?
}
