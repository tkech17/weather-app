package ge.edu.freeuni.weatherapp.utils

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import retrofit2.Retrofit

class RetrofitHelperTest {

    @Test
    fun testGetRetrofitWithURL() {
        val retrofit: Retrofit = getRetrofitWithURL("http://google.com")

        assertNotNull(retrofit)
    }

}