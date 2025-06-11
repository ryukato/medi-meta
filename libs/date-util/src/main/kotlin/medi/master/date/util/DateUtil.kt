package medi.master.date.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import medi.master.date.constants.DEFAULT_DATE_FORMAT
import medi.master.date.constants.DEFAULT_DATE_TIME_FORMAT
import medi.master.date.constants.DEFAULT_HOUR_MINUTES_FORMAT
import medi.master.date.constants.DEFAULT_TIME_FORMAT
import medi.master.date.constants.DEFAULT_ZONE_ID

object DateUtil {
    fun toFormatDate(
        dateTime: LocalDate,
        format: String = DEFAULT_DATE_FORMAT
    ): String {
        return dateTime.format(DateTimeFormatter.ofPattern(format))
    }

    fun toFormatDate(
        dateTime: LocalDateTime,
        zoneId: ZoneId = DEFAULT_ZONE_ID,
        format: String = DEFAULT_DATE_FORMAT
    ): String {
        val zonedDateTime = dateTime.atZone(ZoneId.systemDefault())
            .withZoneSameInstant(zoneId)
        return zonedDateTime.toLocalDate().format(DateTimeFormatter.ofPattern(format))
    }

    fun toFormatTime(
        dateTime: LocalDateTime,
        zoneId: ZoneId = DEFAULT_ZONE_ID,
    ): String {
        val zonedDateTime = dateTime.atZone(ZoneId.systemDefault())
            .withZoneSameInstant(zoneId)
        return zonedDateTime.toLocalTime().format(DateTimeFormatter.ofPattern(DEFAULT_HOUR_MINUTES_FORMAT))
    }

    fun toFormatDateTime(
        dateTime: LocalDateTime,
        zoneId: ZoneId = DEFAULT_ZONE_ID,
        format: String = DEFAULT_DATE_TIME_FORMAT
    ): String {
        val zonedDateTime = dateTime.atZone(ZoneId.systemDefault())
            .withZoneSameInstant(zoneId)
        return zonedDateTime.format(DateTimeFormatter.ofPattern(format))
    }

    fun fromDateAndTime(
        date: String,
        dateFormat: String = DEFAULT_DATE_FORMAT,
        time: String,
        timeFormat: String = DEFAULT_TIME_FORMAT
    ): LocalDateTime {
        val dateTimeValue = date + time
        val formatter = DateTimeFormatter.ofPattern(dateFormat + timeFormat)
        return LocalDateTime.parse(
            dateTimeValue,
            formatter
        )
    }
}
