package ge.edu.freeuni.weatherapp.pages.weather.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ge.edu.freeuni.weatherapp.pages.weather.WeatherFragment


class ViewPagerAdapter(supportFragmentManager: FragmentManager, behaviour: Int, private val countries: List<String>) : FragmentPagerAdapter(supportFragmentManager, behaviour) {
	override fun getItem(position: Int): Fragment {
		val country = countries[position]
		return WeatherFragment.newInstance(country)
	}

	override fun getCount(): Int {
		return countries.size
	}

}
