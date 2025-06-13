package com.firstpark.date.extension

import medi.master.date.util.DateUtil
import java.time.LocalDateTime
import java.util.stream.Stream
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource

class DateUtilTest {
    @ParameterizedTest
    @ArgumentsSource(TestDateTimeValuesProvider::class)
    fun test_fromDateAndTime(expectedAndActualDatetime: ExpectedAndActualDatetime) {
        val datetime = DateUtil.fromDateAndTime(
            date = expectedAndActualDatetime.actualDateValue,
            time = expectedAndActualDatetime.actualTimeValue
        )

        assertEquals(expectedAndActualDatetime.expected, datetime)
    }

    class TestDateTimeValuesProvider : ArgumentsProvider {
        override fun provideArguments(p0: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                Arguments.of(
                    ExpectedAndActualDatetime(
                        expected =  LocalDateTime.of(2024, 1, 1, 1, 1, 1),
                        actualDateValue = "20240101",
                       actualTimeValue = "010101"
                    )
                ),
                Arguments.of(
                    ExpectedAndActualDatetime(
                        expected =  LocalDateTime.of(2024, 2, 29, 23, 59, 59),
                        actualDateValue = "20240229",
                        actualTimeValue = "235959"
                    )
                ),
            )
        }

    }
}

data class ExpectedAndActualDatetime(
    val expected: LocalDateTime,
    val actualDateValue: String,
    val actualTimeValue: String,
)
