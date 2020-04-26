package ge.edu.freeuni.weatherapp.service.country

import ge.edu.freeuni.weatherapp.model.Country

interface CountryManager {

    /**
     * @return current country viewed
     */
    fun getCurrentCountry(): Country

    /**
     * Goes Forward And Saves State
     */
    fun stepForwardAndGet(): Country

    /**
     * Goes Backward And Saves State
     */
    fun stepBackwardAndGet(): Country

}
