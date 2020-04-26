package ge.edu.freeuni.weatherapp

import android.app.Application

class App : Application() {

    companion object {
        const val COUNTRY_API_BASE_URL = "https://restcountries.eu/rest/v2/"
    }

}