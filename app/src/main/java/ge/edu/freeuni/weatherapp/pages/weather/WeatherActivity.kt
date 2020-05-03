package ge.edu.freeuni.weatherapp.pages.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import ge.edu.freeuni.weatherapp.R
import ge.edu.freeuni.weatherapp.model.Country
import ge.edu.freeuni.weatherapp.pages.weather.adapter.ViewPagerAdapter

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
