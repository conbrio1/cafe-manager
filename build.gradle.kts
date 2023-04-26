import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // kotlin
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21" apply false
    kotlin("plugin.jpa") version "1.6.21" apply false
    // spring
    id("org.springframework.boot") version "2.7.11" apply false
    id("io.spring.dependency-management") version "1.0.15.RELEASE" apply false
    // for querydsl
    kotlin("kapt") version "1.6.21" apply false
    // lint
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
}

allprojects {
    group = "com.example.cafe"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

java.sourceCompatibility = JavaVersion.VERSION_17

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    dependencies {
        // jackson
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        // validation
        implementation("org.springframework.boot:spring-boot-starter-validation")
        // reflect
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.getByName("bootJar") {
        enabled = false
    }

    tasks.getByName("jar") {
        enabled = true
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
