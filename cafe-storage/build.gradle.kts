plugins {
    id("org.flywaydb.flyway") version "7.15.0"
}

dependencies {
    // module dependency
    compileOnly(project(":cafe-domain"))
    // spring data jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // querydsl
    implementation("com.querydsl:querydsl-jpa")
    // mysql
    runtimeOnly("com.mysql:mysql-connector-j")
    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2")
}

flyway {
    url = "jdbc:mysql://localhost:3306?useSSL=false&allowPublicKeyRetrieval=true"
    user = "root"
    password = "root"
    locations = arrayOf("filesystem:src/main/resources/db/migration/")
    schemas = arrayOf("cafe")
    createSchemas = true
    encoding = "utf8"
    baselineOnMigrate = true
    cleanDisabled = false
    failOnMissingLocations = true
}
