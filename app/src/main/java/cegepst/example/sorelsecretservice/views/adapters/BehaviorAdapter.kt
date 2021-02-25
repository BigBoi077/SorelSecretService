package cegepst.example.sorelsecretservice.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cegepst.example.sorelsecretservice.R
import cegepst.example.sorelsecretservice.controllers.BehaviorsController

class BehaviorAdapter(private val controller: BehaviorsController) :
    RecyclerView.Adapter<BehaviorAdapter.ViewHolder>() {

    var onDeleteListener: ((Int) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameView = itemView.findViewById<TextView>(R.id.behaviorName)
        private val descriptionView = itemView.findViewById<TextView>(R.id.behaviorDescription)
        private val deleteActionView = itemView.findViewById<ImageButton>(R.id.actionDeleteBehaviorRow)

        fun setContent(name: String, description: String) {
            nameView.text = name
            descriptionView.text = description
        }

        fun bindListener() {
            deleteActionView.setOnClickListener { _ -> onDeleteListener?.invoke(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.row_behavior, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        controller.getAdapterContent(position) { name, description ->
            holder.setContent(
                name,
                description
            )
        }
        holder.bindListener()
    }

    override fun getItemCount(): Int {
        return controller.behaviorsSize
    }
}