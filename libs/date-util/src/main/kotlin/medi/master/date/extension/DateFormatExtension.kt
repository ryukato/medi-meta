package medi.master.date.extension

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

fun LocalDate.toIso8601String(): String {
    return DateTimeFormatter.ISO_DATE.format(this)
}

fun YearMonth.toIso8601String(): String {
    return this.format(DateTimeFormatter.ofPattern("y-MM"))
}
