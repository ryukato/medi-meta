package com.firstpark.date.extension

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.test.assertEquals
import medi.master.date.extension.toEpochMilli
import medi.master.date.extension.toLocalDateTime
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
