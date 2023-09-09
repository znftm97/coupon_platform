package coupon_platform.domain.common.exception.coupon_code

import coupon_platform.domain.common.exception.BaseException
import coupon_platform.domain.common.exception.ExceptionCode

class NotFoundCouponCodeException() : BaseException(ExceptionCode.NOT_FOUND_COUPON_CODE_EXCEPTION)