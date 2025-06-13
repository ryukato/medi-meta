package medi.master.core.domain.api.parser

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class DefaultProductApprovalSourceParserTest {
    private val underTest = DefaultProductApprovalSourceParser()

    @Test
    fun testWithWholeJson() {
        val rawInputJson = this::class.java.classLoader.getResourceAsStream("./test-data/approval-source-details.json")
            ?.use { inputStream ->
                inputStream.bufferedReader().use { it.readText() }
            } ?: ""

        val startedAt = System.currentTimeMillis()
        println("startedAt: $startedAt")
        val parsed = underTest.parse(rawInputJson)
        val finishedAt = System.currentTimeMillis()
        val elapsedTime = finishedAt - startedAt
        println("elapsedTime: $elapsedTime")
        assertNotNull(parsed)
    }
}
