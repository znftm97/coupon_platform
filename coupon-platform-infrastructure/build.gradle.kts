import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":coupon-platform-domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.github.f4b6a3:tsid-creator:5.2.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.redisson:redisson-spring-boot-starter:3.23.2")
}

tasks.register("prepareKotlinBuildScriptModel") {}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true