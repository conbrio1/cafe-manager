package com.example.cafe.domain.common

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.time.OffsetDateTime
import java.util.*

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
class JpaAuditingConfig {

    @Bean
    fun auditingDateTimeProvider() = DateTimeProvider {
        Optional.of(OffsetDateTime.now()) // offsetDateTime을 사용하기 위한 custom DateTimeProvider
    }
}
