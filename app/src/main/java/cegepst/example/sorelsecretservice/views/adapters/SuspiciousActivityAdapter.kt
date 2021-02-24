package cegepst.example.sorelsecretservice.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cegepst.example.sorelsecretservice.R
import cegepst.example.sorelsecretservice.controllers.MainController

class SuspiciousActivityAdapter(private val controller: MainController) :
    RecyclerView.Adapter<SuspiciousActivityAdapter.ViewHolder>() {

    var onDeleteListener: ((Int) -> Unit)? = null
    var onModifyListener: ((Int) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val behaviorView = itemView.findViewById<TextView>(R.id.behaviorText)
        private val locationView = itemView.findViewById<TextView>(R.id.locationText)
        private val confidenceLevelView = itemView.findViewById<TextView>(R.id.trustLevelText)
        private val deleteActionView = itemView.findViewById<ImageButton>(R.id.actionDelete)
        private val modifyActionView = itemView.findViewById<ImageButton>(R.id.actionChange)

        fun setContent(behaviorId: Long, location: String, confidenceLevel: Int) {
            behaviorView.text = behaviorId.toString()
            locationView.text = location
            confidenceLevelView.text = confidenceLevel.toString()
        }

        fun bindListeners() {
            deleteActionView.setOnClickListener { _ -> onDeleteListener?.invoke(adapterPosition) }
            modifyActionView.setOnClickListener { _ -> onModifyListener?.invoke(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.row_suspicious_activity, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        controller.getAdapterContent(position) { behavior, location, confidenceLevel ->
            holder.setContent(behavior, location, confidenceLevel)
        }
        holder.bindListeners()
    }

    override fun getItemCount(): Int {
        return controller.activitiesSize
    }
}