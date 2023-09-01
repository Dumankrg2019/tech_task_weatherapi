package kaz.dev.weatherapp.presentation.main_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kaz.dev.weatherapp.data.ApiService.weatherApiService
import kaz.dev.weatherapp.data.EndPointApi
import kaz.dev.weatherapp.data.WeatherApiService
import kaz.dev.weatherapp.data.responses.city.GetWeatherInTheCity
import kotlinx.coroutines.launch

class WeatherScreenViewModel()
    : ViewModel() {

    private val _weatherData = MutableLiveData<GetWeatherInTheCity>()
    val weatherData: LiveData<GetWeatherInTheCity> get() = _weatherData

    fun fetchWeatherData(key: String, q: String, aqi: String) {
        viewModelScope.launch {
            try {
                val response = weatherApiService.getWeatherOfTheCity(key, q, aqi)
                if(response.isSuccessful) {
                    _weatherData.value = response.body()
                    Log.e("response is", "fetchWeatherData: ${_weatherData.value}", )
                } else {
                    Log.e("", "fetchWeatherData: ERROR")
                }
            } catch (e: Exception) {
                Log.e("catch excetion", "no internet")
            }
        }
    }
}