package coupon_platform.infrastructure.redis.util

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.io.IOException
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class ZonedDateTimeDeserializer : JsonDeserializer<ZonedDateTime>() {
    @Throws(IOException::class)
    override fun deserialize(parser: JsonParser, context: DeserializationContext): ZonedDateTime {
        val timestamp = parser.valueAsString.toDouble()
        val seconds = timestamp.toLong()
        val nanos = ((timestamp - seconds) * 1_000_000_000).toInt()

        return Instant.ofEpochSecond(seconds, nanos.toLong())
            .atZone(ZoneId.of("UTC"))
    }
}