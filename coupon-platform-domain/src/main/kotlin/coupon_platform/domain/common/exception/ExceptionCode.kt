package coupon_platform.domain.common.exception

enum class ExceptionCode(
    val message: String,
) {
    /*common*/
    COMMON_SYSTEM_ERROR("알 수 없는 오류가 발생했습니다."),
    COMMON_INVALID_PARAMETER("올바르지 않은 요청값입니다."),

    /*coupon*/
    NOT_FOUND_COUPON_EXCEPTION("존재하지 않는 쿠폰입니다."),
    INVALID_EXPIRATION_PERIOD_EXCEPTION("만료 기간은 쿠폰 코드를 생성하는 시점의 시간 이후여야 합니다."),

    /*coupon_code*/
    NOT_FOUND_COUPON_CODE_EXCEPTION("존재하지 않는 쿠폰코드입니다."),

    /*etc*/
    INVALID_RANDOM_NUMBER_LENGTH_EXCEPTION("난수 길이는 0 초과여야 합니다.")

}
