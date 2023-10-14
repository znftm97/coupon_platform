package coupon_platform.interfaces.account

import coupon_platform.application.account.AccountFacade
import coupon_platform.interfaces.common.response.BaseResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/accounts/")
class AccountController(
    private val accountFacade: AccountFacade,
) {

    /**
     * 핵심 기능(출석체크)에 집중하기 위해, 실제 로그인 기능은 구현하지 않는다.
     */
    @PostMapping("/login/{userId}")
    fun tmpLogin(@PathVariable("userId") userId: Long): BaseResponse<Unit> {
        accountFacade.login(userId);
        return BaseResponse.success(Unit)
    }
}