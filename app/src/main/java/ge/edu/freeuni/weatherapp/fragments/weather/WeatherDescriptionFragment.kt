package ge.edu.freeuni.weatherapp.fragments.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import ge.edu.freeuni.weatherapp.R

class WeatherDescriptionFragment : Fragment() {

	companion object {

		fun newInstance(): WeatherDescriptionFragment {
			val fragment = WeatherDescriptionFragment()
			return fragment
		}

	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	@SuppressLint("InflateParams")
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view: View = inflater.inflate(R.layout.fragment_weather_description, null)
		val precipitation: LinearLayout = view.findViewById(R.id.weather_description_precipitation)
		val humidity: LinearLayout = view.findViewById(R.id.weather_description_humidity)
		val windSpeed: LinearLayout = view.findViewById(R.id.weather_description_wind_speed)
		val dayAndNight: LinearLayout = view.findViewById(R.id.weather_description_day_and_night)
		setBlockValue(precipitation, getString(R.string.precipitation), "precipitation", true)
		setBlockValue(humidity, getString(R.string.humidity), "humidity", true)
		setBlockValue(windSpeed, getString(R.string.windSpeed), "windSpeed", true)
		setBlockValue(dayAndNight, getString(R.string.dayAndNight), "dayAndNight", true)
		return view
	}

	private fun setBlockValue(lin: LinearLayout, text: String, value: String, isDay: Boolean) {
		for (child in lin.children) {
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


}