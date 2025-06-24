package com.example.wheretonext.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretonext.R
import com.example.wheretonext.data.models.Event


class EventsAdapter(
    private val items: List<Event>,
    private val onItemClick: (Event) -> Unit
) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView = itemView.findViewById<TextView>(R.id.tv_event_name)
        private val dateTextView = itemView.findViewById<TextView>(R.id.tv_event_date)
        private val durationTextView = itemView.findViewById<TextView>(R.id.tv_event_duration)
        private val locationTextView = itemView.findViewById<TextView>(R.id.tv_event_location)
        private val categoryTextView = itemView.findViewById<TextView>(R.id.tv_event_category)

        @SuppressLint("SetTextI18n")
        fun bind(event: Event) {
            nameTextView.text = event.name
            dateTextView.text = event.date.toString()
            durationTextView.text = "${event.duration} min"
            locationTextView.text = event.locationId
            categoryTextView.text = event.categoryId

            itemView.setOnClickListener {
                onItemClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = items.getOrNull(position) ?: return
        holder.bind(event)
    }

    override fun getItemCount(): Int = items.size
}
