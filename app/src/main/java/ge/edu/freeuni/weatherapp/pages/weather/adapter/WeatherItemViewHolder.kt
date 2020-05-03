package ge.edu.freeuni.weatherapp.pages.weather.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.edu.freeuni.weatherapp.R
import ge.edu.freeuni.weatherapp.model.Weather
import ge.edu.freeuni.weatherapp.utils.roundDoubleToInt
import java.lang.Double.parseDouble

class WeatherItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

	private var time: TextView = view.findViewById(R.id.weather_item_time)
	private var icon: ImageView = view.findViewById(R.id.weather_item_icon)
	private var celsius: TextView = view.findViewById(R.id.weather_item_celsius)

	@SuppressLint("SetTextI18n")
	fun setData(weather: Weather) {
		time.text = weather.temp
		celsius.text = "${roundDoubleToInt(weather.temp)}°C"

		if (parseDouble(weather.temp) > 9) {
			icon.setImageResource(R.drawable.ic_sun)
		}else{
			icon.setImageResource(R.drawable.ic_moon)
		}
	}

}