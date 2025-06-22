package com.example.wheretonext.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretonext.R
import com.example.wheretonext.models.Event

class EventsAdapter(
    val items: List<Event>
): RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    inner class EventViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(event:Event) {
            view.findViewById<TextView>(R.id.tv_event_name).text = event.name
            view.findViewById<TextView>(R.id.tv_event_date).text = event.date.toString()
            view.findViewById<TextView>(R.id.tv_event_duration).text = event.duration.toString()
            view.findViewById<TextView>(R.id.tv_event_location).text = event.locationId
            view.findViewById<TextView>(R.id.tv_event_category).text = event.categoryId
            view.findViewById<TextView>(R.id.tv_event_description).text = event.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view  = inflater.inflate(R.layout.item_event, parent, false)

        return EventViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val item = items.getOrNull(position) ?: return

        holder.bind(item)
    }
}