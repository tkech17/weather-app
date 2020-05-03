package ge.edu.freeuni.weatherapp.pages.weather.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ge.edu.freeuni.weatherapp.R
import ge.edu.freeuni.weatherapp.model.WeatherData
import ge.edu.freeuni.weatherapp.utils.convertLocalDateTimeToDate
import ge.edu.freeuni.weatherapp.utils.parseDateHour
import ge.edu.freeuni.weatherapp.utils.parseStrToLocalDateTime
import ge.edu.freeuni.weatherapp.utils.roundDoubleToInt
import java.time.LocalDateTime
import java.util.Date

class WeatherItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

	private var time: TextView = view.findViewById(R.id.weather_item_time)
	private var icon: ImageView = view.findViewById(R.id.weather_item_icon)
	private var celsius: TextView = view.findViewById(R.id.weather_item_celsius)

	@SuppressLint("SetTextI18n")
	fun setData(weather: WeatherData) {
		val localDateTime: LocalDateTime = parseStrToLocalDateTime(weather.dtTxt)
		val date: Date = convertLocalDateTimeToDate(localDateTime)
		time.text = parseDateHour(date)

		celsius.text = "${roundDoubleToInt(weather.main.temp)}°C"

		val iconName = weather.iconWrapper[0].icon
		Picasso.get()
			.load("https://openweathermap.org/img/wn/$iconName@2x.png")
			.error(R.drawable.error_image)
			.into(icon)

	}

}