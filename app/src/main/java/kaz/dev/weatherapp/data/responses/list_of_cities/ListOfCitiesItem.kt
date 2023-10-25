package kaz.dev.weatherapp.data.responses.list_of_cities

data class ListOfCitiesItem(
    val country: String,
    val id: Int,
    val lat: Double,
    val lon: Double,
    val name: String,
    val region: String,
    val url: String
)