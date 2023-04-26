package com.example.cafe.infra.store

import com.example.cafe.domain.user.User
import com.example.cafe.domain.user.UserStore
import com.example.cafe.infra.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserStoreImpl(
    private val userRepository: UserRepository
) : UserStore {
    override fun store(user: User): User {
        return userRepository.save(user)
    }
}
