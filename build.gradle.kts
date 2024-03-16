plugins {
    id("java")
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "dev.sinayko"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.zaxxer:HikariCP")
    implementation("org.liquibase:liquibase-core")
    compileOnly ("org.projectlombok:lombok")
    runtimeOnly ("org.postgresql:postgresql")
    annotationProcessor ("org.projectlombok:lombok")
    implementation("io.awspring.cloud:spring-cloud-aws-dependencies:3.1.0")
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3:3.1.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.adobe.testing:s3mock:3.5.0")
    testImplementation("com.adobe.testing:s3mock-testcontainers:3.5.0")
    testImplementation("org.testcontainers:junit-jupiter:1.19.6")
    testImplementation("software.amazon.awssdk:url-connection-client:2.20.52")
}

tasks.test {
    useJUnitPlatform()
}