package kaz.dev.weatherapp.data

import kaz.dev.weatherapp.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {
    private val api: EndPointApi

    val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()

    val weatherApiService: WeatherApiService

    init {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
            .addInterceptor{chain->
                val origin = chain.request()
                chain.proceed(origin)
            }
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout((90 * 1000).toLong(), TimeUnit.SECONDS)
            .readTimeout((90 * 1000).toLong(), TimeUnit.SECONDS)
            .build()

        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EndPointApi::class.java)
        weatherApiService = WeatherApiService(api)
    }
}