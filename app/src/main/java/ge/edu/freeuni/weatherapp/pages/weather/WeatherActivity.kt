package ge.edu.freeuni.weatherapp.pages.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import ge.edu.freeuni.weatherapp.APP
import ge.edu.freeuni.weatherapp.R
import ge.edu.freeuni.weatherapp.integration.WeatherService
import ge.edu.freeuni.weatherapp.model.City
import ge.edu.freeuni.weatherapp.model.Country
import ge.edu.freeuni.weatherapp.model.Weather
import ge.edu.freeuni.weatherapp.model.WeatherApiResponse
import ge.edu.freeuni.weatherapp.model.WeatherData
import ge.edu.freeuni.weatherapp.utils.getRetrofitWithURL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class WeatherActivity : AppCompatActivity() {


	private lateinit var weatherForecastWrapper: WeatherForecast
	private lateinit var weatherService: WeatherService
	private lateinit var weatherDescriptionWrapper: WeatherDescription
	private lateinit var countries: List<String>

	@Suppress("UNCHECKED_CAST")
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		this.setContentView(R.layout.activity_weather)
		initService()

		countries = (intent.getSerializableExtra("countries") as Array<Country>).asSequence()
			.filter { !it.name.contains("(") && !it.name.contains(" ") }
			.map { it.name }
			.toList()

		val weatherForecastView: ConstraintLayout = getWeatherForecastLayout()
		weatherForecastWrapper = WeatherForecast(weatherForecastView)
		val weatherDescView: ConstraintLayout = getWeatherDescriptionLayout()
		weatherDescriptionWrapper = WeatherDescription(weatherDescView)
		downloadWeatherByCountry(countries.first())
	}


	private fun initService() {
		val retrofit: Retrofit = getRetrofitWithURL(APP.WEATHER_FORECAST_BASE_URL)
		weatherService = retrofit.create(WeatherService::class.java)
	}

	private fun getWeatherForecastLayout(): ConstraintLayout {
		val weatherActivity: ConstraintLayout = findViewById(R.id.activity_main_forecast_wrapper)
		return weatherActivity.children
			.filter { it.id == R.id.activity_weather_include_forecast }
			.first() as ConstraintLayout
	}

	private fun getWeatherDescriptionLayout(): ConstraintLayout {
		val weatherActivity: ConstraintLayout = findViewById(R.id.activity_main_description_wrapper)
		return weatherActivity.children
			.filter { it.id == R.id.activity_weather_include_description }
			.first() as ConstraintLayout
	}

	private fun setValues(apiResponse: WeatherApiResponse) {
		val currentWeather: WeatherData = apiResponse.list[0]
		weatherDescriptionWrapper.setValues(currentWeather)
		weatherForecastWrapper.setValues(apiResponse)
	}

	private fun downloadWeatherByCountry(country: String) {
		val retrofit: Retrofit = getRetrofitWithURL(APP.WEATHER_FORECAST_BASE_URL)
		weatherService = retrofit.create(WeatherService::class.java)

		weatherService.getCountryWeather(country).enqueue(object : Callback<WeatherApiResponse> {
			override fun onFailure(call: Call<WeatherApiResponse>, t: Throwable) {
				t.message?.let { Log.e("BLA", it) }
			}

			override fun onResponse(call: Call<WeatherApiResponse>, response: Response<WeatherApiResponse>) {
				if (response.isSuccessful) {
					setValues(response.body()!!)
				} else {
					onFailure(call, Throwable())
				}
			}

		})
	}

}


class WeatherDescription(private val descriptionLayout: ConstraintLayout) {

	private val precipitation: LinearLayout = descriptionLayout.findViewById(R.id.weather_description_precipitation)
	private val humidity: LinearLayout = descriptionLayout.findViewById(R.id.weather_description_humidity)
	private val windSpeed: LinearLayout = descriptionLayout.findViewById(R.id.weather_description_wind_speed)
	private val dayAndNight: LinearLayout = descriptionLayout.findViewById(R.id.weather_description_day_and_night)


	fun setValues(currentWeather: WeatherData) {
		val resources = descriptionLayout.resources
		setBlockValue(precipitation, resources.getString(R.string.precipitation), "precipitation", true)
		setBlockValue(humidity, resources.getString(R.string.humidity), currentWeather.main.humidity, true)
		setBlockValue(windSpeed, resources.getString(R.string.windSpeed), currentWeather.wind.speed, true)
		setBlockValue(dayAndNight, resources.getString(R.string.dayAndNight), "dayAndNight", true)
	}

	private fun setBlockValue(lin: LinearLayout, text: String, value: String, isDay: Boolean) {
		val child: View = lin.children.first()
		val blockIcon: ImageView = child.findViewById(R.id.detailed_block_icon)
		val textView: TextView = child.findViewById(R.id.detailed_block_text)
		val valueView: TextView = child.findViewById(R.id.detailed_block_value)
		textView.text = text
		valueView.text = value
		if (isDay) {
			blockIcon.setImageResource(R.drawable.ic_drop)
		}
	}

}

class WeatherForecast(private val forecastLayout: ConstraintLayout) {

	private val countryTextView: TextView = forecastLayout.findViewById(R.id.weather_forecast_country)
	private val dayTextView: TextView = forecastLayout.findViewById(R.id.weather_forecast_day)
	private val celsiusTextView: TextView = forecastLayout.findViewById(R.id.weather_forecast_celsius)
	private val perceivedCelsiusTextView: TextView = forecastLayout.findViewById(R.id.weather_forecast_perceived_celsius)
	private val weatherIconView: ImageView = forecastLayout.findViewById(R.id.weather_forecast_day_or_night)


	@SuppressLint("SetTextI18n")
	fun setValues(apiResponse: WeatherApiResponse) {
		val currentWeather: Weather = apiResponse.list[0].main
		val city: City = apiResponse.city

		if (currentWeather.isDay()) {
			forecastLayout.background = forecastLayout.context!!.getDrawable(R.drawable.day_background)
			weatherIconView.setImageResource(R.drawable.ic_sun)
		} else {
			forecastLayout.background = forecastLayout.context!!.getDrawable(R.drawable.night_background)
			weatherIconView.setImageResource(R.drawable.ic_moon)
		}
		countryTextView.text = city.name
		dayTextView.text = "Monday 17 gen 18 6:32 am"
		celsiusTextView.text = currentWeather.temp + "°C"
		perceivedCelsiusTextView.text = "perceived " + currentWeather.feelsLike + "°C"
	}

}

private fun Weather.isDay(): Boolean {
	return true
}
