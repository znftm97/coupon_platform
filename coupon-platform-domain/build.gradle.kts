import org.springframework.boot.gradle.tasks.bundling.BootJar

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

plugins {
    kotlin("plugin.jpa") version "1.8.10"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

tasks.register("prepareKotlinBuildScriptModel") {}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true