package ge.edu.freeuni.weatherapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import ge.edu.freeuni.weatherapp.integration.WeatherService
import ge.edu.freeuni.weatherapp.model.Country
import ge.edu.freeuni.weatherapp.utils.getRetrofitWithURL
import pl.droidsonroids.gif.GifImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {


    private lateinit var loadingGif: GifImageView
    private lateinit var tryAgainButton: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingGif = findViewById(R.id.main_activity_loading_gif)
        tryAgainButton = findViewById(R.id.main_activity_try_again_button)
        tryAgainButton.setOnClickListener {
            getCountriesFromNet()
            it.visibility = View.INVISIBLE
        }

        getCountriesFromNet()
    }

    private fun getCountriesFromNet() {
        val retrofit: Retrofit = getRetrofitWithURL(App.COUNTRY_API_BASE_URL)
        val service: WeatherService = retrofit.create(WeatherService::class.java)
        loadingGif.visibility = View.VISIBLE

        service.getAllCountries().enqueue(object : Callback<List<Country>> {

            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                loadingGif.visibility = View.INVISIBLE

            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                loadingGif.visibility = View.INVISIBLE
                tryAgainButton.visibility = View.VISIBLE
            }

        })
    }


}
