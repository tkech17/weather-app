package ge.edu.freeuni.weatherapp.pages.weather

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import ge.edu.freeuni.weatherapp.APP
import ge.edu.freeuni.weatherapp.R
import ge.edu.freeuni.weatherapp.fragments.weather.WeatherForecastFragment
import ge.edu.freeuni.weatherapp.fragments.weather.adapter.WeatherForecastAdapter
import ge.edu.freeuni.weatherapp.integration.WeatherService
import ge.edu.freeuni.weatherapp.model.Country
import ge.edu.freeuni.weatherapp.model.Weather
import ge.edu.freeuni.weatherapp.model.WeatherData
import ge.edu.freeuni.weatherapp.model.Wind
import ge.edu.freeuni.weatherapp.utils.getRetrofitWithURL
import retrofit2.Retrofit

class WeatherActivity : AppCompatActivity(), WeatherDataGetter {


	private lateinit var weatherForecastAdapter: WeatherForecastAdapter
	private lateinit var weatherService: WeatherService

	private lateinit var precipitation: LinearLayout
	private lateinit var humidity: LinearLayout
	private lateinit var windSpeed: LinearLayout
	private lateinit var dayAndNight: LinearLayout


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		this.setContentView(R.layout.activity_weather)
		val weatherDescActivity: View = getWeatherDescriptionLayout()
		precipitation = weatherDescActivity.findViewById(R.id.weather_description_precipitation)
		humidity = weatherDescActivity.findViewById(R.id.weather_description_humidity)
		windSpeed = weatherDescActivity.findViewById(R.id.weather_description_wind_speed)
		dayAndNight = weatherDescActivity.findViewById(R.id.weather_description_day_and_night)
		initService()
		initFragments()
	}

	override fun onAttachFragment(fragment: Fragment) {
		if (fragment is WeatherForecastFragment) {
			fragment.setWeatherDataGetter(this)
		}
	}

	private fun getWeatherDescriptionLayout(): View {
		val weatherActivity: ConstraintLayout = findViewById(R.id.activity_main)
		return weatherActivity.children
			.filter { it.id == R.id.activity_weather_include_description }
			.first()
	}

	private fun initService() {
		val retrofit: Retrofit = getRetrofitWithURL(APP.WEATHER_FORECAST_BASE_URL)
		weatherService = retrofit.create(WeatherService::class.java)
	}

	@Suppress("UNCHECKED_CAST")
	private fun initFragments() {
		val weatherForecastPager: ViewPager =
			findViewById(R.id.activity_weather_forecast_view_pager)
		val countries: Array<Country> = intent.getSerializableExtra("countries") as Array<Country>
		weatherForecastAdapter = WeatherForecastAdapter(
			supportFragmentManager,
			0,
			countries.filter { !it.name.contains("(") && !it.name.contains(" ") }
		)
		weatherForecastPager.adapter = weatherForecastAdapter
	}

	fun setValues(result: List<WeatherData>) {
		val currentWeather: WeatherData = result[0]
		weatherForecastAdapter.setValues(currentWeather.main)
		setBlockValue(precipitation, getString(R.string.precipitation), "precipitation", true)
		setBlockValue(humidity, getString(R.string.humidity), currentWeather.main.humidity, true)
		setBlockValue(windSpeed, getString(R.string.windSpeed), currentWeather.wind.speed, true)
		setBlockValue(dayAndNight, getString(R.string.dayAndNight), "dayAndNight", true)
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

	override fun downloadWeatherByCountry(country: String) {
		val retrofit: Retrofit = getRetrofitWithURL(APP.WEATHER_FORECAST_BASE_URL)
		weatherService = retrofit.create(WeatherService::class.java)

		val result: List<WeatherData> = listOf(WeatherData(Wind("123"), Weather("13", "123", "15")))
		setValues(result)
//		weatherService.getCountryWeather(country).enqueue(object : Callback<WeatherApiResponse> {
//			override fun onFailure(call: Call<WeatherApiResponse>, t: Throwable) {
//				t.message?.let { Log.e("BLA", it) }
//			}
//
//			override fun onResponse(
//				call: Call<WeatherApiResponse>,
//				response: Response<WeatherApiResponse>
//			) {
//				if (response.isSuccessful) {
//					val result: List<WeatherData> = response.body()!!.list
//					setValues(result)
//				} else {
//					onFailure(call, Throwable())
//				}
//			}
//
//		})
	}

}