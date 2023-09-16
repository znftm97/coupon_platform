package coupon_platform.infrastructure

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["coupon_platform"])
class ApplicationTests {
    fun contextLoads() {}
}