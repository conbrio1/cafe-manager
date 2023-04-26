package com.example.cafe.infra.reader

import com.example.cafe.domain.user.Role
import com.example.cafe.domain.user.User
import com.example.cafe.domain.user.UserReader
import com.example.cafe.infra.repository.RoleRepository
import com.example.cafe.infra.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserReaderImpl(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) : UserReader {
    override fun getUserByPhoneNumber(phoneNumber: String): User? {
        return userRepository.findByPhoneNumber(phoneNumber)
    }

    override fun getRoleByRoleName(role: String): List<Role> {
        return roleRepository.findByName(role)
    }
}
