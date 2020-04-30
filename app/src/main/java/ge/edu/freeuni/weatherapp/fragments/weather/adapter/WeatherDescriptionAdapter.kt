package ge.edu.freeuni.weatherapp.fragments.weather.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ge.edu.freeuni.weatherapp.fragments.weather.WeatherDescriptionFragment


class WeatherDescriptionAdapter(fm: FragmentManager, behavior: Int) :
	FragmentStatePagerAdapter(fm, behavior) {

	override fun getItem(position: Int): Fragment {
		return WeatherDescriptionFragment.newInstance()
	}

	override fun getCount(): Int {
		return 1
	}

}