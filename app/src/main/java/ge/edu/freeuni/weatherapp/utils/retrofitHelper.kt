package ge.edu.freeuni.weatherapp.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun getRetrofitWithURL(baseURL: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}