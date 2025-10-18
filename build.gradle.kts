plugins {
    alias(libs.plugins.java)
    alias(libs.plugins.jacoco)
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.checkstyle)
	alias(libs.plugins.springframework.boot)
	alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.flywaydb)
}

group = "com.roman3455"
version = "0.1.0-SNAPSHOT"
description = "GitHub & Docker Hub updates in Telegram"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(libs.versions.java.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
    // === Spring Boot ===
	implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    // === Database ===
    implementation(libs.flyway.core)
    implementation(libs.flyway.postgresql)
    runtimeOnly(libs.postgresql)
    // === Utility ===
    implementation(libs.spring.dotenv)
    // === Test ===
	testImplementation(libs.spring.boot.starter.test)
	testRuntimeOnly(libs.junit.jupiter)
}

sonar {
    properties {
        property ("sonar.projectKey", "Roman3455_DeplifyBot2")
        property ("sonar.organization", "roman3455")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
        html.required = true
    }
}

tasks.bootJar {
    archiveFileName.set("app-$version.jar")
}

tasks.register<Exec>("dockerBuild") {
    group = "docker"
    description = "Build Docker image with version from Gradle"
    commandLine(
        "docker", "build",
        "--build-arg", "APP_VERSION=$version",
//        "-t", "roman3455/deplifybot:$version",
        "-t", "roman3455/deplifybot:latest",
        "."
    )
}

flyway {
    url = System.getenv("FLYWAY_URL")
    user = System.getenv("FLYWAY_USER")
    password = System.getenv("FLYWAY_PASSWORD")
    locations = arrayOf("classpath:db/migration")
}
