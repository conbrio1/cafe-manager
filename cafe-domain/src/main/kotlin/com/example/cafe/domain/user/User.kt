package com.example.cafe.domain.user

import com.example.cafe.domain.common.BaseEntity
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class User private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false, unique = true)
    val phoneNumber: String,
    @Column(nullable = false)
    var password: String
) : BaseEntity() {
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _userRoles: MutableList<UserRole> = mutableListOf()
    val userRoles: List<UserRole>
        get() = _userRoles

    constructor(
        phoneNumber: String,
        password: String
    ) : this(
        id = 0L,
        phoneNumber = phoneNumber,
        password = password
    )

    fun changePassword(password: String) {
        this.password = password
    }

    fun addRole(role: Role) {
        val userRole = UserRole(this, role)
        _userRoles.add(userRole)
    }
}
