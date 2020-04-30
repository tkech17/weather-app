package ge.edu.freeuni.weatherapp.utils

import android.icu.text.SimpleDateFormat
import java.util.Date

fun parseDate(date: String): Date {
	return SimpleDateFormat("yyyy-dd-MM").parse(date)
}