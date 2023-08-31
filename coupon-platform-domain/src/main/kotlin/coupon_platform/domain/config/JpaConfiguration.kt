package coupon_platform.domain.config

import coupon_platform.domain.Domain
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaAuditing
@EntityScan(basePackageClasses = [Domain::class])
@EnableJpaRepositories(basePackageClasses = [Domain::class])
class JpaConfiguration {
}