package coupon_platform.interfaces

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["coupon_platform"])
class CouponPlatformInterfacesApplication

fun main(args: Array<String>) {
    runApplication<CouponPlatformInterfacesApplication>(*args)
}
