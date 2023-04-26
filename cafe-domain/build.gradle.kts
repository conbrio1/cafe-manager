plugins {
    kotlin("plugin.jpa")
    kotlin("kapt")
}

dependencies {
    compileOnly("org.springframework.boot:spring-boot-starter-data-jpa")
    kapt("com.querydsl:querydsl-apt::jpa")
    implementation("com.querydsl:querydsl-jpa")
}
