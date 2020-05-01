package ge.edu.freeuni.weatherapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherApiResponse(
	val list: List<WeatherData>
) : Serializable

data class WeatherData(
	val wind: Wind,
    val main: Weather
) : Serializable

data class Weather(
	val temp: String,
	val humidity: String,
	@SerializedName("feels_like")
	val feelsLike: String
) : Serializable

data class Wind(val speed: String) : Serializable