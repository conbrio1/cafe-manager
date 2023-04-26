package com.example.cafe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EntityScan(basePackages = ["com.example.cafe.domain"])
@EnableJpaRepositories(basePackages = ["com.example.cafe.infra.repository"])
@SpringBootApplication(scanBasePackages = ["com.example.cafe"])
class CafeApiApplication

fun main(args: Array<String>) {
    runApplication<CafeApiApplication>(*args)
}
