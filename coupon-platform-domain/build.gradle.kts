import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

tasks.register("prepareKotlinBuildScriptModel"){}