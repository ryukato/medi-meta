package medi.master.date.extension

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import medi.master.date.constants.DEFAULT_ZONE_ID

fun LocalDateTime.toEpochMilli(zoneId: ZoneId = DEFAULT_ZONE_ID): Long {
    return this.truncatedTo(ChronoUnit.MILLIS).atZone(zoneId).toInstant().toEpochMilli()
}

fun LocalDate.toEpochMilli(zoneId: ZoneId = DEFAULT_ZONE_ID): Long {
    return this.atStartOfDay(zoneId).toInstant().toEpochMilli();
}

fun Long.toLocalDateTime(zoneId: ZoneId = DEFAULT_ZONE_ID): LocalDateTime {
    return Instant.ofEpochMilli(this).atZone(zoneId).toLocalDateTime()
}

fun Long.toLocalDate(zoneId: ZoneId = DEFAULT_ZONE_ID): LocalDate {
    return Instant.ofEpochMilli(this).atZone(zoneId).toLocalDateTime().toLocalDate()
}


fun LocalDateTime.toFormatDateTime(format: String = "yyyyMMddHHmmss"): String {
    return this.format(DateTimeFormatter.ofPattern(format))
}

fun LocalDate.toFormatDate(format: String = "yyyyMMdd"): String {
    return this.format(DateTimeFormatter.ofPattern(format))
}
