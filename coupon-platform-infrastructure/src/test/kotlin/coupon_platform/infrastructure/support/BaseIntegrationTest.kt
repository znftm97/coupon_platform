package coupon_platform.infrastructure.support

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql

@SpringBootTest
@ActiveProfiles("test")
@Sql(value = ["/data/init.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = ["/data/clean-up.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
interface BaseIntegrationTest