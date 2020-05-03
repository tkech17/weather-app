package ge.edu.freeuni.weatherapp

import android.app.Application

class APP : Application() {

	companion object {
		const val COUNTRY_API_BASE_URL = "https://restcountries.eu/rest/v2/"
		const val WEATHER_FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/"
		const val API_KEY = "f6973761e0a69667328ceeb3636f507f"
	}


}