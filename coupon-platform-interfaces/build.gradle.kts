dependencies {
    implementation(project(":coupon-platform-application"))
    implementation(project(":coupon-platform-domain"))
    implementation(project(":coupon-platform-infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("io.rest-assured:rest-assured:5.3.0")
    testImplementation("io.rest-assured:kotlin-extensions:5.3.0")
}

tasks.register("prepareKotlinBuildScriptModel") {}