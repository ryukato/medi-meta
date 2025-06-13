package medi.master.extensions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class StringExtensionTest {
    @ParameterizedTest
    @CsvSource(
        "ITEM_NAME, itemName",
        "USER_ID, userId",
        "camelCase, camelCase",
        "AlreadyCamel, AlreadyCamel",
        "DOC, DOC",
        "html_parser, htmlParser"
    )
    fun `test snakeToCamelIfApplicable`(input: String, expected: String) {
        val actual = input.toCamelCaseIfApplicable()
        assertEquals(expected, actual)
    }
}
