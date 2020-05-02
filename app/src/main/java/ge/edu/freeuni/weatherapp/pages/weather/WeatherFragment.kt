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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ge.edu.freeuni.weatherapp.APP
import ge.edu.freeuni.weatherapp.R
import ge.edu.freeuni.weatherapp.integration.WeatherService
import ge.edu.freeuni.weatherapp.model.City
import ge.edu.freeuni.weatherapp.model.Weather
import ge.edu.freeuni.weatherapp.model.WeatherApiResponse
import ge.edu.freeuni.weatherapp.model.WeatherData
import ge.edu.freeuni.weatherapp.pages.weather.adapter.RecyclerViewAdapter
import ge.edu.freeuni.weatherapp.utils.getRetrofitWithURL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

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


	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val view: View = inflater.inflate(R.layout.fragment_weather, null)
		initService()
		initRecyclerView(view)


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

	private fun setValues(apiResponse: WeatherApiResponse) {
		val currentWeather: WeatherData = apiResponse.list[0]
		weatherDescriptionWrapper.setValues(currentWeather)
		weatherForecastWrapper.setValues(apiResponse)
		adapter.setData(
			listOf(
				currentWeather.main,
				currentWeather.main,
				currentWeather.main,
				currentWeather.main,
				currentWeather.main,
				currentWeather.main,
				currentWeather.main,
				currentWeather.main,
				currentWeather.main,
				currentWeather.main,
				currentWeather.main,
				currentWeather.main,
				currentWeather.main,
				currentWeather.main,
				currentWeather.main,
				currentWeather.main,
				currentWeather.main,
				currentWeather.main,
				currentWeather.main
			)
		)
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

	private val precipitation: ConstraintLayout = descriptionLayout.findViewById(R.id.weather_description_precipitation)
	private val humidity: ConstraintLayout = descriptionLayout.findViewById(R.id.weather_description_humidity)
	private val windSpeed: ConstraintLayout = descriptionLayout.findViewById(R.id.weather_description_wind_speed)
	private val dayAndNight: ConstraintLayout = descriptionLayout.findViewById(R.id.weather_description_day_and_night)


	fun setValues(currentWeather: WeatherData) {
		val resources = descriptionLayout.resources
		setBlockValue(precipitation, resources.getString(R.string.precipitation), "precipitation", true)
		setBlockValue(humidity, resources.getString(R.string.humidity), currentWeather.main.humidity + "%", true)
		setBlockValue(windSpeed, resources.getString(R.string.windSpeed), currentWeather.wind.speed + " kmp", true)
		setBlockValue(dayAndNight, resources.getString(R.string.dayAndNight), "dayAndNight", true)
	}

	private fun setBlockValue(lin: ConstraintLayout, text: String, value: String, isDay: Boolean) {
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
