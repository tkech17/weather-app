package ge.edu.freeuni.weatherapp.pages.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import ge.edu.freeuni.weatherapp.R
import ge.edu.freeuni.weatherapp.model.Country

class WeatherActivity : AppCompatActivity() {

	private lateinit var viewPager: ViewPager

	@Suppress("UNCHECKED_CAST")
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_weather)
		viewPager = findViewById(R.id.viewPager)

		val countries = (intent.getSerializableExtra("countries") as Array<Country>).asSequence()
			.filter { !it.name.contains("(") && !it.name.contains(" ") }
			.map { it.name }
			.toList()

		val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, 0, countries)
		viewPager.adapter = viewPagerAdapter
	}

}

class ViewPagerAdapter(supportFragmentManager: FragmentManager, behaviour: Int, private val countries: List<String>) : FragmentPagerAdapter(supportFragmentManager, behaviour) {
	override fun getItem(position: Int): Fragment {
		val country = countries[position]
		return WeatherFragment.newInstance(country)
	}

	override fun getCount(): Int {
		return countries.size
	}

}
