package com.example.cafe.application

import com.example.cafe.application.dto.command.UserCommand
import com.example.cafe.application.dto.info.UserInfo
import com.example.cafe.common.exception.CafeExceptionType
import com.example.cafe.common.exception.CafeRuntimeException
import com.example.cafe.domain.user.User
import com.example.cafe.domain.user.UserReader
import com.example.cafe.domain.user.UserStore
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class UserService(
    private val passwordEncoder: PasswordEncoder,
    private val userReader: UserReader,
    private val userStore: UserStore
) {

    @Transactional
    fun createUser(userCreateCommand: UserCommand.UserCreateCommand): UserInfo.UserInfo {
        validateDuplicateUser(userCreateCommand.phoneNumber)

        val user = userCreateCommand.toEntity(passwordEncoder)

        validateAndAddRole(user, userCreateCommand.role)

        val createdUser = userStore.store(user)
        return UserInfo.UserInfo.of(createdUser)
    }

    private fun validateDuplicateUser(phoneNumber: String) {
        userReader.getUserByPhoneNumber(phoneNumber)?.let {
            throw CafeRuntimeException(CafeExceptionType.USER_DUPLICATION)
        }
    }

    private fun validateAndAddRole(user: User, role: String) {
        val foundRoles = userReader.getRoleByRoleName(role)
        if (foundRoles.isEmpty()) {
            throw CafeRuntimeException(CafeExceptionType.ROLE_NOT_FOUND)
        }
        foundRoles.forEach { user.addRole(it) }
    }
}
