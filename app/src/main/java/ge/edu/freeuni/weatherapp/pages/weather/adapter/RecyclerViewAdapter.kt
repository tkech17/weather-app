package ge.edu.freeuni.weatherapp.pages.weather.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.edu.freeuni.weatherapp.R
import ge.edu.freeuni.weatherapp.model.Weather

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private val countries: MutableList<Weather> = mutableListOf()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val view: View = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
		return WeatherItemViewHolder(view)
	}

	override fun getItemCount(): Int {
		return countries.size
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val weather: Weather = countries[position]
		val weatherViewHolder: WeatherItemViewHolder = holder as WeatherItemViewHolder
		Log.e("BLA", "index :  $position")
		weatherViewHolder.setData(weather)
	}

	fun setData(countries: List<Weather>) {
		this.countries.clear()
		this.countries.addAll(countries)
		this.notifyDataSetChanged()
	}

}
