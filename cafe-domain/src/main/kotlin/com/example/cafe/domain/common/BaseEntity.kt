package com.example.cafe.domain.common

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.OffsetDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    lateinit var createdAt: OffsetDateTime
        protected set

    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    lateinit var updatedAt: OffsetDateTime
        protected set
}
