package ge.edu.freeuni.weatherapp.integration


import ge.edu.freeuni.weatherapp.APP
import ge.edu.freeuni.weatherapp.model.WeatherApiResponse
import ge.edu.freeuni.weatherapp.model.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {

    @GET("weather")
    fun getCurrentWeather(
        @Query("q") countryName: String,
        @Query("units") units: String = "metric",
        @Query("appid") accessKey: String = APP.API_KEY
    ): Call<WeatherData>

}