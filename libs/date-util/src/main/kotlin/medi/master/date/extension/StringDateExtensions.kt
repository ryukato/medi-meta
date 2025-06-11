package medi.master.date.extension

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toLocalDateTime(
    format: String = "yyyyMMddHHmmss"
): LocalDateTime {
    return LocalDateTime.parse(this, DateTimeFormatter.ofPattern(format))
}

fun String.toLocalDate(
    format: String = "yyyyMMdd"
): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ofPattern(format))
}

fun String.dateToLocalDateTime(
    format: String = "yyyyMMdd"
): LocalDateTime {
    val epoch = LocalDate.parse(this, DateTimeFormatter.ofPattern(format)).toEpochMilli()
    return epoch.toLocalDateTime()

}
