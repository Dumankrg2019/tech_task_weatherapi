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
import kaz.dev.weatherapp.data.responses.list_of_cities.ListOfCities
import kotlinx.coroutines.launch

class WeatherScreenViewModel()
    : ViewModel() {

    private val _weatherData = MutableLiveData<GetWeatherInTheCity>()
    private val _listOfCities = MutableLiveData<ListOfCities>()
    val weatherData: LiveData<GetWeatherInTheCity> get() = _weatherData
    val listOfCities: LiveData<ListOfCities> get() = _listOfCities
    val loading = MutableLiveData<Boolean>()
    val errorStatus = MutableLiveData<Boolean>()

    fun fetchWeatherData(key: String, q: String, aqi: String) {
        loading.value = true
        viewModelScope.launch {
            try {
                val response = weatherApiService.getWeatherOfTheCity(key, q, aqi)
                if(response.isSuccessful) {
                    _weatherData.value = response.body()
                    Log.e("response is", "fetchWeatherData: ${_weatherData.value}", )
                    loading.value = false
                    errorStatus.value = false
                } else {
                    Log.e("", "fetchWeatherData: ERROR")
                    loading.value = false
                    errorStatus.value = true
                }
            } catch (e: Exception) {
                Log.e("catch excetion", "no internet")
                loading.value = false
            }
        }
    }

    fun fetchListOfCities(key: String, q: String) {
        loading.value = true
        viewModelScope.launch {
            try {
                val response = weatherApiService.getListOfCities(key, q)
                if(response.isSuccessful) {
                    _listOfCities.value = response.body()
                    Log.e("response is", "fetchWeatherData: ${_listOfCities.value}", )
                    loading.value = false
                    errorStatus.value = false
                } else {
                    Log.e("", "fetchWeatherData: ERROR")
                    loading.value = false
                    errorStatus.value = true
                }
            } catch (e: Exception) {
                Log.e("catch excetion", "no internet")
                loading.value = false
            }
        }
    }
}