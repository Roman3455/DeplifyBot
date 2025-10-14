plugins {
    alias(libs.plugins.java)
    alias(libs.plugins.jacoco)
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.checkstyle)
	alias(libs.plugins.springframework.boot)
	alias(libs.plugins.spring.dependency.management)
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
