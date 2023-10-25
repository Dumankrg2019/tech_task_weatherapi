package kaz.dev.weatherapp.presentation.main_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kaz.dev.weatherapp.R
import kaz.dev.weatherapp.data.responses.city.GetWeatherInTheCity
import kaz.dev.weatherapp.data.responses.list_of_cities.ListOfCities
import kaz.dev.weatherapp.data.responses.list_of_cities.ListOfCitiesItem

class HistoryQueryAdapter(
    val historyQueryList: ArrayList<ListOfCitiesItem>,
    val temperatureOfCity: GetWeatherInTheCity
): RecyclerView.Adapter<HistoryQueryAdapter.HistoryQueryViewHolder>() {

    class HistoryQueryViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val historyTitle = view.findViewById<TextView>(R.id.tvQueryTitle)
        val temperatureOfCity = view.findViewById<TextView>(R.id.tvTemperatureValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryQueryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_history_query, parent, false)
    )

    override fun onBindViewHolder(holder: HistoryQueryViewHolder, position: Int) {
        holder.historyTitle.text = historyQueryList.get(position).name
        holder.temperatureOfCity.text = temperatureOfCity.current.temp_c.toString()
    }

    override fun getItemCount(): Int {
        return historyQueryList.size
    }
}