package medi.master.date.extension

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DateFormatExtensionTest {

    @Test
    fun `localDate toIso8601String()`() {
        val inputDate = LocalDate.of(2023, 1, 13)
        val expected = "2023-01-13"
        val actual = inputDate.toIso8601String()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `yearMonth toIso8601String()`() {
        val inputDate = YearMonth.of(2023, 1)
        val expected = "2023-01"
        val actual = inputDate.toIso8601String()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun testToFormatDateTime() {
        val dateTime = LocalDateTime.of(2024, 9, 1, 12, 0, 0)
        val expectedDateTimeString = "20240901120000"
        val actual = dateTime.toFormatDateTime()
        assertEquals(expectedDateTimeString, actual)
    }
}
