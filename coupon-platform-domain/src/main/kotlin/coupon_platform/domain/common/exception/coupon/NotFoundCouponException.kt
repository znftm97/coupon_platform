package coupon_platform.domain.common.exception.coupon

import coupon_platform.domain.common.exception.BaseException
import coupon_platform.domain.common.exception.ExceptionCode

class NotFoundCouponException() : BaseException(ExceptionCode.NOT_FOUND_COUPON_EXCEPTION)