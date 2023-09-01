package kaz.dev.weatherapp.data

import kaz.dev.weatherapp.data.responses.city.GetWeatherInTheCity
import retrofit2.Response

class WeatherApiService(private val api: EndPointApi) {

    suspend fun getWeatherOfTheCity(key:String, q:String, aqi: String)
    : Response<GetWeatherInTheCity> {
        return api.getWeatherInTheCity(key, q, aqi)
    }
}