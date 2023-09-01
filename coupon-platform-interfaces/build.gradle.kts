dependencies {
    api(project(":coupon-platform-core"))
    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.register("prepareKotlinBuildScriptModel") {}