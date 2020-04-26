package ge.edu.freeuni.weatherapp.integration


import ge.edu.freeuni.weatherapp.model.Country
import ge.edu.freeuni.weatherapp.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY: String = "aab32b519f020a859f8ef04845a6c350"

interface WeatherService {

    @GET("all")
    fun getAllCountries(): Call<List<Country>>

    @GET("current")
    fun getCountryWeather(
        @Query("query") countryName: String,
        @Query("access_key") accessKey: String = API_KEY
    ): Call<Weather>

}