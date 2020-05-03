package ge.edu.freeuni.weatherapp.pages.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ge.edu.freeuni.weatherapp.APP
import ge.edu.freeuni.weatherapp.R
import ge.edu.freeuni.weatherapp.integration.WeatherService
import ge.edu.freeuni.weatherapp.model.City
import ge.edu.freeuni.weatherapp.model.Weather
import ge.edu.freeuni.weatherapp.model.WeatherApiResponse
import ge.edu.freeuni.weatherapp.pages.weather.adapter.RecyclerViewAdapter
import ge.edu.freeuni.weatherapp.utils.getRetrofitWithURL
import ge.edu.freeuni.weatherapp.utils.parseDateHour
import ge.edu.freeuni.weatherapp.utils.roundDoubleToInt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Calendar
import java.util.Date

class WeatherFragment : Fragment() {

	companion object {

		fun newInstance(countryName: String): WeatherFragment {
			val fragment = WeatherFragment()
			val args = Bundle()
			args.putString("country", countryName)
			fragment.arguments = args
			return fragment
		}

	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		country = arguments!!.getString("country")!!
	}

	private lateinit var country: String
	private lateinit var weatherService: WeatherService
	private lateinit var weatherForecastWrapper: WeatherForecast
	private lateinit var weatherDescriptionWrapper: WeatherDescription
	private lateinit var recyclerView: RecyclerView
	private lateinit var adapter: RecyclerViewAdapter
	private lateinit var wrapperLayout: ConstraintLayout
	private lateinit var errorTextView: TextView


	@SuppressLint("InflateParams")
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val view: View = inflater.inflate(R.layout.fragment_weather, null)
		initService()
		initRecyclerView(view)

		wrapperLayout = view.findViewById(R.id.wrapper)
		errorTextView = view.findViewById(R.id.fragment_weather_error_string)
		val weatherForecastView: ConstraintLayout = getWeatherForecastLayout(view)
		weatherForecastWrapper = WeatherForecast(weatherForecastView)
		val weatherDescView: ConstraintLayout = getWeatherDescriptionLayout(view)
		weatherDescriptionWrapper = WeatherDescription(weatherDescView)
		downloadWeatherByCountry(country)
		return view
	}

	private fun initRecyclerView(view: View) {
		recyclerView = view.findViewById(R.id.activity_main_recycler_view)
		adapter = RecyclerViewAdapter()
		recyclerView.adapter = adapter
		recyclerView.layoutManager = LinearLayoutManager(view.context)
		val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
		recyclerView.addItemDecoration(decoration)
	}


	private fun initService() {
		val retrofit: Retrofit = getRetrofitWithURL(APP.WEATHER_FORECAST_BASE_URL)
		weatherService = retrofit.create(WeatherService::class.java)
	}

	private fun getWeatherForecastLayout(view: View): ConstraintLayout {
		val weatherActivity: ConstraintLayout = view.findViewById(R.id.activity_main_forecast_wrapper)
		return weatherActivity.children
			.filter { it.id == R.id.activity_weather_include_forecast }
			.first() as ConstraintLayout
	}

	private fun getWeatherDescriptionLayout(view: View): ConstraintLayout {
		val weatherActivity: ConstraintLayout = view.findViewById(R.id.activity_main_description_wrapper)
		return weatherActivity.children
			.filter { it.id == R.id.activity_weather_include_description }
			.first() as ConstraintLayout
	}

	private fun setValues(response: WeatherApiResponse) {
		weatherForecastWrapper.setValues(response)
		weatherDescriptionWrapper.setValues(response)

		val weathers = response.list
		adapter.setData(weathers)
	}

	private fun downloadWeatherByCountry(country: String) {
		val retrofit: Retrofit = getRetrofitWithURL(APP.WEATHER_FORECAST_BASE_URL)
		weatherService = retrofit.create(WeatherService::class.java)

		weatherService.getCurrentWeather(country).enqueue(object : Callback<WeatherApiResponse> {
			@SuppressLint("SetTextI18n")
			override fun onFailure(call: Call<WeatherApiResponse>, t: Throwable) {
				t.message?.let { Log.e(WeatherFragment::class.simpleName, it) }
				wrapperLayout.visibility = View.INVISIBLE
				errorTextView.text = "error while trying to load $country weather info"
				errorTextView.visibility = View.VISIBLE
			}

			override fun onResponse(call: Call<WeatherApiResponse>, response: Response<WeatherApiResponse>) {
				if (response.isSuccessful) {
					wrapperLayout.visibility = View.VISIBLE
					errorTextView.visibility = View.INVISIBLE
					val result: WeatherApiResponse = response.body()!!
					setValues(result)
				} else {
					onFailure(call, Throwable())
				}
			}

		})
	}

}


class WeatherDescription(private val descriptionLayout: ConstraintLayout) {

	private val precipitation: ConstraintLayout = descriptionLayout.findViewById(R.id.weather_description_precipitation)
	private val humidity: ConstraintLayout = descriptionLayout.findViewById(R.id.weather_description_humidity)
	private val windSpeed: ConstraintLayout = descriptionLayout.findViewById(R.id.weather_description_wind_speed)
	private val dayAndNight: ConstraintLayout = descriptionLayout.findViewById(R.id.weather_description_day_and_night)


	fun setValues(response: WeatherApiResponse) {
		val currentWeatherData = response.list[0]
		val currentWeather: Weather = currentWeatherData.main
		val resources = descriptionLayout.resources
		setBlockValue(precipitation, resources.getString(R.string.precipitation), currentWeatherData.clouds.all + "%", getDrawableByDay(R.drawable.ic_drop, R.drawable.ic_drop_night))
		setBlockValue(humidity, resources.getString(R.string.humidity), currentWeather.humidity + "%", getDrawableByDay(R.drawable.ic_humidity, R.drawable.ic_humidity_night))
		setBlockValue(windSpeed, resources.getString(R.string.windSpeed), roundDoubleToInt(currentWeatherData.wind.speed) + "kmp", getDrawableByDay(R.drawable.ic_flag, R.drawable.ic_flag_night))
		setBlockValue(dayAndNight, resources.getString(R.string.dayAndNight), getSunriseSunsetStr(response.city), R.drawable.ic_day_night)
	}

	private fun getSunriseSunsetStr(city: City): String {
		val sunRise = Date(city.sunrise)
		val sunSet = Date(city.sunset)
		return "${parseDateHour(sunRise)} ${parseDateHour(sunSet)}"
	}

	private fun setBlockValue(lin: ConstraintLayout, text: String, value: String, icon: Int) {
		val child: View = lin.children.first()
		val blockIcon: ImageView = child.findViewById(R.id.detailed_block_icon)
		val textView: TextView = child.findViewById(R.id.detailed_block_text)
		val valueView: TextView = child.findViewById(R.id.detailed_block_value)
		textView.text = text
		valueView.text = value
		blockIcon.setImageResource(icon)
	}

	private fun getDrawableByDay(dayDrawable: Int, nightDrawable: Int): Int {
		if (isDay()) {
			return dayDrawable
		}
		return nightDrawable
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

		if (isDay()) {
			forecastLayout.background = forecastLayout.context!!.getDrawable(R.drawable.day_background)
			weatherIconView.setImageResource(R.drawable.ic_sun)
		} else {
			forecastLayout.background = forecastLayout.context!!.getDrawable(R.drawable.night_background)
			weatherIconView.setImageResource(R.drawable.ic_moon)
		}
		countryTextView.text = city.name
		celsiusTextView.text = "${roundDoubleToInt(currentWeather.temp)}°C"
		perceivedCelsiusTextView.text = "Perceived " + roundDoubleToInt(currentWeather.feelsLike) + "°C"
		dayTextView.text = getCurrentDateTimeText()
	}

	private fun getCurrentDateTimeText(): String {
		val zonedDateTime: ZonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault())
		val dateStr = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).format(zonedDateTime)
		var indexOf = dateStr.indexOf(" AM ")
		if (indexOf < 0) {
			indexOf = dateStr.indexOf(" PM ")
		}
		return dateStr.substring(0, indexOf + 3)
	}

}


private fun isDay(): Boolean {
	val c: Calendar = Calendar.getInstance()
	val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)
	if (timeOfDay in 7..19) {
		return true
	}
	return false
}