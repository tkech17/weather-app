package ge.edu.freeuni.weatherapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherApiResponse(val list: List<WeatherData>, val city: City)
data class WeatherData(val wind: Wind, val main: Weather)
data class Weather(val temp: String, val humidity: String, @SerializedName("feels_like") val feelsLike: String)
data class Wind(val speed: String) : Serializable
data class City(val name: String)
