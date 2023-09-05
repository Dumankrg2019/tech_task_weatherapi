package kaz.dev.weatherapp.presentation.main_screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.postDelayed
import androidx.lifecycle.ViewModelProvider
import kaz.dev.weatherapp.R
import kaz.dev.weatherapp.data.ApiService.weatherApiService
import kaz.dev.weatherapp.databinding.FragmentWeatherScreenBinding
import kaz.dev.weatherapp.utils.API_KEY


class WeatherScreenFragment : Fragment() {

    private val TAG = "weatherScreenFragment"
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

        checkFieldAndGetQuery(binding.etCityName)
    }

    private fun observeOn(){
        viewModel.fetchWeatherData(API_KEY, "Almaty", "no")
        viewModel.weatherData.observe(viewLifecycleOwner, {weatherInfo->
            weatherInfo?.let {
                binding.tvLocationCity.setText(it.location.name)
                binding.tvLocationCountry.setText(it.location.country)
                binding.tvTemperature.setText("${it.current.temp_c}°C")
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, {isLoading->
            isLoading?.let{
                binding.progressBar.visibility =if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.errorStatus.observe(viewLifecycleOwner, {isErrorStatus->
            isErrorStatus?.let {
                if(it == true) {
                    inflateTextValueWhenErrorStatus()
                }
            }
        })
    }

    private fun checkFieldAndGetQuery(editText: EditText) {
        val delay = 300L
        val handler = Handler(Looper.getMainLooper())
        editText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
              handler.postDelayed({
                  if(s.toString().length >= 2) {
                      viewModel
                          .fetchWeatherData(
                              API_KEY,
                              s.toString(),
                              "no"
                          )
                  }
              }, delay)
            }

        })
    }

    private fun inflateTextValueWhenErrorStatus() {
        binding.tvTemperature.text = "Value"
        binding.tvLocationCity.text = "Location City"
        binding.tvLocationCountry.text = "Location Country"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}