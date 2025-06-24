package com.example.wheretonext.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretonext.R
import com.example.wheretonext.data.models.Event


class EventsAdapter(
    private var events: List<Event>,
    private val onItemClick: (Event) -> Unit,
    private val onDetailsClick: (Event) -> Unit
) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView = itemView.findViewById<TextView>(R.id.tv_event_name)
        private val dateTextView = itemView.findViewById<TextView>(R.id.tv_event_date)
        private val locationTextView = itemView.findViewById<TextView>(R.id.tv_event_location)
        private val categoryTextView = itemView.findViewById<TextView>(R.id.tv_event_category)

        private val detailsButton = itemView.findViewById<Button>(R.id.btn_details)

        @SuppressLint("SetTextI18n")
        fun bind(event: Event) {
            nameTextView.text = event.name
            dateTextView.text = event.date.toString()
            locationTextView.text = event.locationId
            categoryTextView.text = event.categoryId

            itemView.setOnClickListener {
                onItemClick(event)
            }

            detailsButton.setOnClickListener {
                onDetailsClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events.getOrNull(position) ?: return
        holder.bind(event)
    }

    fun updateList(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = events.size
}
