plugins {
}

dependencies {
    // module dependency
    implementation(project(":cafe-domain"))
    implementation(project(":cafe-storage"))
    // web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // security
    implementation("org.springframework.boot:spring-boot-starter-security")
    // authentication
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    // open api
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.7.0")
    // test
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
