package kaz.dev.weatherapp.data.responses.city

data class GetWeatherInTheCity(
    val current: Current,
    val location: Location
)