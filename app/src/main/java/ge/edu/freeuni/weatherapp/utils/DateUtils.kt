package ge.edu.freeuni.weatherapp.utils

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun parseDateHour(date: Date): String {
	val dateFormat: DateFormat = SimpleDateFormat("hh:mma")
	return dateFormat.format(date)
}

fun parseStrToLocalDateTime(date: String): LocalDateTime {
	val dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
	return dateTime
}

fun convertLocalDateTimeToDate(localDateTime: LocalDateTime): Date {
	return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
}

