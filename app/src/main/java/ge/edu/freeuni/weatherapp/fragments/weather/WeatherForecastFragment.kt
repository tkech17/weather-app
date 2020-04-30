package ge.edu.freeuni.weatherapp.fragments.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import ge.edu.freeuni.weatherapp.R
import ge.edu.freeuni.weatherapp.model.Country
import ge.edu.freeuni.weatherapp.model.Weather

class WeatherForecastFragment : Fragment() {

    companion object {

        fun newInstance(country: Country, weather: Weather): WeatherForecastFragment {
            val fragment = WeatherForecastFragment()
            val args = Bundle()
            args.putSerializable("country", country)
            args.putSerializable("weather", weather)
            fragment.arguments = args
            return fragment
        }

    }

    private lateinit var weather: Weather

    private lateinit var country: Country
    private lateinit var layout: ConstraintLayout
    private lateinit var countryTextView: TextView
    private lateinit var dayTextView: TextView
    private lateinit var celsiusTextView: TextView
    private lateinit var perceivedCelsiusTextView: TextView

    private lateinit var weatherIconView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weather = arguments!!.getSerializable("weather") as Weather
        country = arguments!!.getSerializable("country") as Country
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_weather_forecast, null)
        layout = view.findViewById(R.id.fragment_weather_layout)
        countryTextView = view.findViewById(R.id.weather_forecast_country)
        dayTextView = view.findViewById(R.id.weather_forecast_day)
        celsiusTextView = view.findViewById(R.id.weather_forecast_celsius)
        perceivedCelsiusTextView = view.findViewById(R.id.weather_forecast_perceived_celsius)
        weatherIconView = view.findViewById(R.id.weather_forecast_day_or_night)
        setValues()
        return view
    }

    @SuppressLint("SetTextI18n")
    private fun setValues() {
        if (isDay()) {
            layout.background = context!!.getDrawable(R.drawable.day_background)
            weatherIconView.setImageResource(R.drawable.ic_sun)
        } else {
            layout.background = context!!.getDrawable(R.drawable.night_background)
            weatherIconView.setImageResource(R.drawable.ic_moon)
        }
        countryTextView.text = country.name
        dayTextView.text = "Monday 17 gen 18 6:32 am"
        celsiusTextView.text = weather.temp + "°C"
        perceivedCelsiusTextView.text = "perceived " + weather.feelsLike + "°C"

    }

    private fun isDay(): Boolean {
        return true
    }

}