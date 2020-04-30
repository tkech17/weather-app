package ge.edu.freeuni.weatherapp.pages.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import ge.edu.freeuni.weatherapp.R
import ge.edu.freeuni.weatherapp.fragments.weather.adapter.WeatherDescriptionAdapter
import ge.edu.freeuni.weatherapp.fragments.weather.adapter.WeatherForecastAdapter
import ge.edu.freeuni.weatherapp.model.Country
import ge.edu.freeuni.weatherapp.service.country.CountryManager
import ge.edu.freeuni.weatherapp.service.country.countryManagerOf

class WeatherActivity : AppCompatActivity() {

	private lateinit var weatherForecastPager: ViewPager
	private lateinit var weatherDescriptionPager: ViewPager
	private lateinit var countryManager: CountryManager

	@Suppress("UNCHECKED_CAST")
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		this.setContentView(R.layout.activity_weather)
        weatherForecastPager = findViewById(R.id.activity_weather_forecast_view_pager)
        weatherDescriptionPager = findViewById(R.id.activity_weather_description_view_pager)

        val countries: Array<Country> = intent.getSerializableExtra("countries") as Array<Country>
        countryManager = countryManagerOf(countries.toList())

		val weatherForecastAdapter = WeatherForecastAdapter(supportFragmentManager, 0, countries.toList())
		val weatherDescriptionAdapter = WeatherDescriptionAdapter(supportFragmentManager, 0)
		weatherForecastPager.adapter = weatherForecastAdapter
        weatherDescriptionPager.adapter = weatherDescriptionAdapter
	}

}