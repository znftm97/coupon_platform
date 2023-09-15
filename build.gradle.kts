import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.spring") version "1.8.10"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = "com.coupon_platform"
    version = "0.0.1-SNAPSHOT"

    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")

        plugin("kotlin")
        plugin("kotlin-spring")
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
        runtimeOnly("com.mysql:mysql-connector-j")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation(kotlin("test"))
        testImplementation("io.mockk:mockk:1.13.4")
        runtimeOnly("io.kotest:kotest-assertions-core-jvm:5.7.0")
        testImplementation("io.kotest:kotest-runner-junit5-jvm:5.7.0")
        testImplementation("io.kotest:kotest-framework-datatest:5.7.0")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true