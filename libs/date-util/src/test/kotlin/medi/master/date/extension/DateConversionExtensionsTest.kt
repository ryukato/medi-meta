package medi.master.date.extension

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class DateConversionExtensionsTest {
    @Test
    fun test() {
        val now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)
        val epochMillis = now.toEpochMilli()
        val reverted = epochMillis.toLocalDateTime()
        assertEquals(now, reverted)
    }
}
