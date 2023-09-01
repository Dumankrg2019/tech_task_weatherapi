package kaz.dev.weatherapp.presentation.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kaz.dev.weatherapp.R
import kaz.dev.weatherapp.data.ApiService.weatherApiService
import kaz.dev.weatherapp.databinding.FragmentWeatherScreenBinding
import kaz.dev.weatherapp.utils.API_KEY


class WeatherScreenFragment : Fragment() {

    private var _binding: FragmentWeatherScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[WeatherScreenViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observeOn()
    }

    private fun observeOn(){
        viewModel.fetchWeatherData(API_KEY, "Almaty", "no")
        viewModel.weatherData.observe(viewLifecycleOwner, {weatherInfo->
            weatherInfo?.let {
                binding.tvWeather.text = it.toString()
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}