package kaz.dev.weatherapp.data

import kaz.dev.weatherapp.data.responses.city.GetWeatherInTheCity
import kaz.dev.weatherapp.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface EndPointApi {
    @Headers(
        "Content-Type: application/json",
    )

    @GET("current.json")
    suspend fun getWeatherInTheCity(@Query("key") apiKey: String,
                            @Query("q") location: String,
                            @Query("aqi") aqi: String
    ): Response<GetWeatherInTheCity>
}