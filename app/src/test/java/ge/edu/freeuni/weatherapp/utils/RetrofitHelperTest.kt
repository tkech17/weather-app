package ge.edu.freeuni.weatherapp.utils

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.test.Ignore

class RetrofitHelperTest {

    @Test
    fun testGetRetrofitWithURL() {
        val retrofit: Retrofit = getRetrofitWithURL("http://google.com")

        assertNotNull(retrofit)
    }

}