package com.example.wheretonext.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretonext.R
import com.example.wheretonext.adapters.EventsAdapter
import com.example.wheretonext.data.AppDatabase
import com.example.wheretonext.data.models.Event
import com.example.wheretonext.ui.events.EventsFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment: Fragment() {
    private lateinit var userRole: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userRole = arguments?.getString("userRole") ?: "user"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addIcon = view.findViewById<TextView>(R.id.add_icon)

        // Show or hide add icon based on user role
        if (userRole == "admin") {
            addIcon.visibility = View.VISIBLE
            addIcon.setOnClickListener {
                goToForm()
            }
        } else {
            addIcon.visibility = View.GONE
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.eventsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val spinnerLocation = view.findViewById<Spinner>(R.id.spinner_location)
        val spinnerCategory = view.findViewById<Spinner>(R.id.spinner_category)

        val db = AppDatabase.getDatabase(requireContext())
        val eventDao = db.eventDAO
        val locationDao = db.locationDAO
        val categoryDao = db.categoryDAO

        lifecycleScope.launch {
            // get locations, categories and events
            val locations = withContext(Dispatchers.IO) { locationDao.getAll() }
            val categories = withContext(Dispatchers.IO) { categoryDao.getAll() }
            val events = withContext(Dispatchers.IO) { eventDao.getAll() }

            val locationNames = listOf("All Locations") + locations.map { it.name }
            val categoryNames = listOf("All Categories") + categories.map { it.name }

            spinnerLocation.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, locationNames).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            spinnerCategory.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryNames).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

            val locationMap = locations.associateBy({ it.id }, { it.name })
            val categoryMap = categories.associateBy({ it.id }, { it.name })

            val adapter = EventsAdapter(events, locationMap, categoryMap,
                onItemClick = { event ->
                    Toast.makeText(requireContext(), "Clicked: ${event.name}", Toast.LENGTH_SHORT).show()
                },
                onDetailsClick = { event ->
                    val action = EventsFragmentDirections.actionHomeFragmentToEventDetailsFragment(userRole, event.id)
                    findNavController().navigate(action)
                }
            )

            recyclerView.apply {
                this.layoutManager = LinearLayoutManager(requireContext())
                this.adapter = adapter
            }

            fun filterEvents() {
                val selectedLocation = spinnerLocation.selectedItem.toString()
                val selectedCategory = spinnerCategory.selectedItem.toString()

                val filtered = events.filter { event ->
                    val matchesLocation = selectedLocation == "All Locations" || locations.find { it.id == event.locationId }?.name == selectedLocation
                    val matchesCategory = selectedCategory == "All Categories" || categories.find { it.id == event.categoryId }?.name == selectedCategory
                    matchesLocation && matchesCategory
                }
                adapter.updateList(filtered)
            }

            spinnerLocation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) = filterEvents()
                override fun onNothingSelected(parent: AdapterView<*>) = filterEvents()
            }

            spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) = filterEvents()
                override fun onNothingSelected(parent: AdapterView<*>) = filterEvents()
            }
        }
    }

    private fun goToForm() {
        val action = EventsFragmentDirections.actionHomeFragmentToAddEventFragment()
        findNavController().navigate(action)
    }
}