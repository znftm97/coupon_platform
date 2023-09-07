dependencies {
    implementation(project(":coupon-platform-application"))
    implementation(project(":coupon-platform-domain"))
    runtimeOnly(project(":coupon-platform-infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.register("prepareKotlinBuildScriptModel") {}