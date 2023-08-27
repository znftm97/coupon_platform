import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    api(project(":coupon-platform-application"))
}

tasks.register("prepareKotlinBuildScriptModel"){}
