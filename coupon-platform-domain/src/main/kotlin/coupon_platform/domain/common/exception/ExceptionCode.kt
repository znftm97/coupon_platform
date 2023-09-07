package coupon_platform.domain.common.exception

enum class ExceptionCode(
    val message: String,
) {
    /*common*/
    COMMON_SYSTEM_ERROR("알 수 없는 오류가 발생했습니다."),
    COMMON_INVALID_PARAMETER("올바르지 않은 요청값입니다."),

    /*coupon*/
    NOT_FOUND_COUPON_EXCEPTION("존재하지 않는 쿠폰입니다."),

    /*coupon_code*/
    NOT_FOUND_COUPON_CODE_EXCEPTION("존재하지 않는 쿠폰코드입니다."),

}
