import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    api(project(":coupon-platform-domain"))
}

tasks.register("prepareKotlinBuildScriptModel"){}
