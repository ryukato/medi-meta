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

    @Test
    fun testEpochToLocalDateTime() {
        val now = LocalDateTime.of(1955, 4, 12, 9,31, 1 )
        val epochMillis = -464655539000L
        val reverted = epochMillis.toLocalDateTime()
        assertEquals(now, reverted)
    }
}
