package ge.edu.freeuni.weatherapp.fragments.weather.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ge.edu.freeuni.weatherapp.fragments.weather.WeatherForecastFragment
import ge.edu.freeuni.weatherapp.model.Country
import ge.edu.freeuni.weatherapp.model.Weather
import ge.edu.freeuni.weatherapp.model.WeatherInfo
import ge.edu.freeuni.weatherapp.model.Wind

class WeatherForecastAdapter(fm: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fm, behavior) {

    private lateinit var countries: List<Country>

    constructor(fm: FragmentManager, behavior: Int, countries: List<Country>) : this(fm, behavior) {
        this.countries = countries
    }

    override fun getItem(position: Int): Fragment {
        val country: Country = countries[position]
        return WeatherForecastFragment.newInstance(
            country, Weather(
                "121", "1", "1", "1", "1", "1", "1", "1", "1",
                WeatherInfo("asd", "asd", ""),
                Wind("123")
            )
        )
    }

    override fun getCount(): Int {
        return countries.size
    }

}