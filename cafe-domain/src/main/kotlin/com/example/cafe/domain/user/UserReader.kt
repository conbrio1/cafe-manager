package com.example.cafe.domain.user

interface UserReader {

    fun getUserByPhoneNumber(phoneNumber: String): User?

    fun getRoleByRoleName(role: String): List<Role>
}
