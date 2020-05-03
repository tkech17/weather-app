package ge.edu.freeuni.weatherapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherApiResponse(val list: List<WeatherData>, val city: City)
data class WeatherData(@SerializedName("weather")val iconWrapper: List<IconWrapper>, val wind: Wind, val main: Weather, val clouds: Cloud, @SerializedName("dt_txt") val dtTxt: String)
data class Cloud(val all: String)
data class IconWrapper(val icon: String)
data class Weather(val temp: String, val humidity: String, @SerializedName("feels_like") val feelsLike: String, var dateTxt: String?)
data class Wind(val speed: String) : Serializable
data class City(val name: String, val sunrise: Long, val sunset: Long)
