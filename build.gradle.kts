plugins {
    alias(libs.plugins.java)
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

tasks.withType<Test> {
	useJUnitPlatform()
}
