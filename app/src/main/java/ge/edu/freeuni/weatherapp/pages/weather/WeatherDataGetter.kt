package ge.edu.freeuni.weatherapp.pages.weather

interface WeatherDataGetter {

	fun downloadWeatherByCountry(country: String)

}