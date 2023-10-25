package kaz.dev.weatherapp.presentation.main_screen

import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kaz.dev.weatherapp.databinding.FragmentWeatherScreenBinding
import kaz.dev.weatherapp.presentation.main_screen.adapter.HistoryQueryAdapter
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

        //val arrayOfQueries = arrayListOf<String>("gonzo", "amateur", "home", "hide camera", "primal", "family")

        binding.etCityName.setOnClickListener {
            binding.rvHistoryOfQueries.visibility = View.VISIBLE
        }

        binding.mainLayout.getViewTreeObserver().addOnGlobalLayoutListener {
            val r = Rect()
            binding.mainLayout.getWindowVisibleDisplayFrame(r)
            val screenHeight: Int = binding.mainLayout.getRootView().getHeight()
            val keypadHeight: Int = screenHeight - r.bottom
            if (keypadHeight > screenHeight * 0.15) {
//                Toast.makeText(requireActivity(), "Keyboard is showing", Toast.LENGTH_LONG)
//                    .show()
                Log.e("keyboard status", "Keyboard is showing")
            } else {
                //Toast.makeText(requireActivity(), "keyboard closed", Toast.LENGTH_LONG).show()
                Log.e("keyboard status", "Keyboard is closed")
            }
        }

    }

    private fun observeOn(){
        viewModel.fetchWeatherData(API_KEY, "Almaty", "no")
        viewModel.listOfCities.observe(viewLifecycleOwner, {listOfCities->
            listOfCities?.let {
                Log.e("loc", "list of cities: ${it}")
                val queryHistoryAdapter = HistoryQueryAdapter(it, viewModel.weatherData.value!!)

                binding.rvHistoryOfQueries.apply {
                    adapter = queryHistoryAdapter
                    layoutManager = LinearLayoutManager(requireActivity())
                    this.setHasFixedSize(true)
                }
            }
        })
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
                      viewModel.fetchListOfCities(API_KEY,s.toString())
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