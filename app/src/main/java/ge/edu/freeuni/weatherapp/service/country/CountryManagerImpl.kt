package ge.edu.freeuni.weatherapp.service.country

import ge.edu.freeuni.weatherapp.model.Country

/**
 * @param countries countries list, with size > 0
 */
class CountryManagerImpl(private val countries: List<Country>) : CountryManager {

    private var currentCountryIndex: Int = -1

    override fun getCurrentCountry(): Country {
        return countries[currentCountryIndex]
    }

    override fun stepForwardAndGet(): Country {
        currentCountryIndex = (currentCountryIndex + 1) % countries.size
        return countries[currentCountryIndex]
    }

    override fun stepBackwardAndGet(): Country {
        currentCountryIndex = (countries.size + currentCountryIndex - 1) % countries.size
        return countries[currentCountryIndex]
    }

}