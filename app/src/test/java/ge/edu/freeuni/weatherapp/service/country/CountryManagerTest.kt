package ge.edu.freeuni.weatherapp.service.country

import ge.edu.freeuni.weatherapp.model.Country
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CountryManagerTest {

    @Test
    fun testGetCurrentCountry() {
        val countries: List<Country> = listOf(Country("georgia"), Country("denmark"), Country("sweden"))
        val countryManager: CountryManager = countryManagerOf(countries)

        val currentCountry: Country = countryManager.getCurrentCountry()
        val expectedCurrentCountry: Country = countries[0]

        assertEquals(expectedCurrentCountry, currentCountry)
    }

    @Test
    fun testStepForwardAndGet() {
        val countries: List<Country> = listOf(Country("georgia"), Country("denmark"), Country("sweden"))
        val countryManager: CountryManager = countryManagerOf(countries)

        val currentCountry: Country = countryManager.stepForwardAndGet()
        val expectedCurrentCountry: Country = countries[1]

        assertEquals(expectedCurrentCountry, currentCountry)
        assertEquals(currentCountry, countryManager.getCurrentCountry())
    }

    @Test
    fun testStepBackwardAndGet() {
        val countries: List<Country> = listOf(Country("georgia"), Country("denmark"), Country("sweden"))
        val countryManager: CountryManager = countryManagerOf(countries)

        val currentCountry: Country = countryManager.stepBackwardAndGet()
        val expectedCurrentCountry: Country = countries[2]

        assertEquals(expectedCurrentCountry, currentCountry)
        assertEquals(currentCountry, countryManager.getCurrentCountry())
    }

}