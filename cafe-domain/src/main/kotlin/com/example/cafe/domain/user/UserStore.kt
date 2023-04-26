package com.example.cafe.domain.user

interface UserStore {
    fun store(user: User): User
}
