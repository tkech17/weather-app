package ge.edu.freeuni.weatherapp.integration

import ge.edu.freeuni.weatherapp.model.Country
import retrofit2.Call
import retrofit2.http.GET

interface CountriesService {

    @GET("all")
    fun getAllCountries(): Call<List<Country>>

}