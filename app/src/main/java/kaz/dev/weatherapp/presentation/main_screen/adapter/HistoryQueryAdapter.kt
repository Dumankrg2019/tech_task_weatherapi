package kaz.dev.weatherapp.presentation.main_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kaz.dev.weatherapp.R

class HistoryQueryAdapter(
    val historyQueryList: ArrayList<String>
): RecyclerView.Adapter<HistoryQueryAdapter.HistoryQueryViewHolder>() {

    class HistoryQueryViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val historyTitle = view.findViewById<TextView>(R.id.tvQueryTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryQueryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_history_query, parent, false)
    )

    override fun onBindViewHolder(holder: HistoryQueryViewHolder, position: Int) {
        holder.historyTitle.text = historyQueryList.get(position)
    }

    override fun getItemCount(): Int {
        return historyQueryList.size
    }
}