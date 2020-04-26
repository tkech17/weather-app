package ge.edu.freeuni.weatherapp.model

data class Weather(val request: Request)

data class Request(val type: String, val query: String, val language: String, val unit: String)