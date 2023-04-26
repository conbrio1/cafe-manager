package com.example.cafe.domain.user

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Role private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val name: String
) {
    constructor(
        name: String
    ) : this(
        id = 0,
        name = name
    )
}
