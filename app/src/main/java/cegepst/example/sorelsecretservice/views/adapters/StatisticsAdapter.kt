package cegepst.example.sorelsecretservice.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cegepst.example.sorelsecretservice.R
import cegepst.example.sorelsecretservice.controllers.StatisticsController

class StatisticsAdapter(private val controller: StatisticsController) :
        RecyclerView.Adapter<StatisticsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val locationTextView = itemView.findViewById<TextView>(R.id.location)
        private val percentageTextView = itemView.findViewById<TextView>(R.id.percentage)

        fun setContent(location: String, percentage: String) {
            locationTextView.text = location
            percentageTextView.text = percentage
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        controller.getAdapterContent(position) { location, percentage ->
            holder.setContent(
                    location,
                    percentage
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.row_statistic, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return controller.statisticsSize
    }
}