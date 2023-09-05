dependencies {
    api(project(":coupon-platform-domain"))
    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.register("prepareKotlinBuildScriptModel") {}