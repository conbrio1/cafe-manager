package com.example.cafe.domain.user

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class UserRole private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    val role: Role
) {
    constructor(
        user: User,
        role: Role
    ) : this(
        id = 0L,
        user = user,
        role = role
    )
}
