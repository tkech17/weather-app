package ge.edu.freeuni.weatherapp.utils

import java.lang.Double.parseDouble

fun roundDoubleToInt(doubleVal: String) = parseDouble(doubleVal).toInt().toString()
