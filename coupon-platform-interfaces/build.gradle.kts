
dependencies {
    api(project(":coupon-platform-application"))
    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.register("prepareKotlinBuildScriptModel"){}