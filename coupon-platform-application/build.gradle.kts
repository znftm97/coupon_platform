import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    api(project(":coupon-platform-domain"))
}

tasks.register("prepareKotlinBuildScriptModel"){}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true
