import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":coupon-platform-domain"))
    implementation(project(":coupon-platform-application"))

    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.redisson:redisson-spring-boot-starter:3.23.2")
    testImplementation("org.springframework.batch:spring-batch-test")
}

tasks.register("prepareKotlinBuildScriptModel") {}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = true
jar.enabled = false