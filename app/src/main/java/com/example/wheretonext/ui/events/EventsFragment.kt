package com.example.wheretonext.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretonext.R
import com.example.wheretonext.adapters.EventsAdapter
import com.example.wheretonext.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val recyclerView = view.findViewById<RecyclerView>(R.id.eventsRecyclerView)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            val db = AppDatabase.getDatabase(requireContext())
            val eventDao = db.eventDAO

            // load all events from DB
            lifecycleScope.launch {
                val events = withContext(Dispatchers.IO) {
                    eventDao.getAll()
                }

                val adapter = EventsAdapter(events) { event ->
                    Toast.makeText(requireContext(), "Clicked: ${event.name}", Toast.LENGTH_SHORT).show()
                }

            val layoutManager = LinearLayoutManager(requireContext())

            recyclerView.apply {
                this.layoutManager = layoutManager
                this.adapter = adapter
            }
        }
    }
}