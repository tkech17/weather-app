package ge.edu.freeuni.weatherapp.fragments.weather.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ge.edu.freeuni.weatherapp.fragments.weather.WeatherForecastFragment
import ge.edu.freeuni.weatherapp.model.Country
import ge.edu.freeuni.weatherapp.model.Weather
import ge.edu.freeuni.weatherapp.pages.weather.WeatherDataGetter

class WeatherForecastAdapter(fm: FragmentManager, behavior: Int) :
	FragmentStatePagerAdapter(fm, behavior) {

	private lateinit var countries: List<Country>
	private lateinit var fragmentInstance: WeatherForecastFragment

	constructor(
		fm: FragmentManager,
		behavior: Int,
		countries: List<Country>
	) : this(fm, behavior) {
		this.countries = countries
	}

	override fun getItem(position: Int): Fragment {
		val country: Country = countries[position]
		fragmentInstance = WeatherForecastFragment.newInstance(country)
		return fragmentInstance
	}

	override fun getItemPosition(`object`: Any): Int {
		return POSITION_NONE
	}

	override fun getCount(): Int {
		return countries.size
	}

	fun setValues(currentWeather: Weather) {
		fragmentInstance.setValues(currentWeather)
	}

}