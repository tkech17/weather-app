package ge.edu.freeuni.weatherapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherApiResponse(val data: List<WeatherData>)

data class WeatherData(val weather: Weather)

data class Weather(
    val temp: String,
    val humidity: String,
    val pressure: String,
    @SerializedName("feels_like")
    val feelsLike: String,
    @SerializedName("temp_min")
    val tempMin: String,
    @SerializedName("temp_max")
    val tempMax: String,
    @SerializedName("sea_level")
    val seaLevel: String,
    @SerializedName("grnd_level")
    val groundLevel: String,
    @SerializedName("temp_kf")
    val tempKF: String,
    val weather: WeatherInfo,
    val wind: Wind
) : Serializable

data class WeatherInfo(
    @SerializedName("main")
    val info: String,
    @SerializedName("description")
    val description: String,
    val icon: String

) : Serializable

data class Wind(val speed: String)