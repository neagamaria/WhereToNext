package com.example.wheretonext.ui.events

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.wheretonext.R
import com.example.wheretonext.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_details, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = EventDetailsFragmentArgs.fromBundle(requireArguments())
        val eventId = args.eventId

        val db = AppDatabase.getDatabase(requireContext())
        val eventDao = db.eventDAO


        lifecycleScope.launch {
            val event = withContext(Dispatchers.IO) {
                eventDao.findById(eventId)
            }

            event?.let {
                view.findViewById<TextView>(R.id.tv_event_name).text = it.name
                view.findViewById<TextView>(R.id.tv_event_date).text = it.date.toString()
                view.findViewById<TextView>(R.id.tv_event_duration).text = it.duration.toString()
                view.findViewById<TextView>(R.id.tv_event_location).text = it.locationId
                view.findViewById<TextView>(R.id.tv_event_category).text = it.categoryId
                view.findViewById<TextView>(R.id.tv_event_description).text = it.description
            }
        }
        val userRole = arguments?.getString("userRole") ?: "user"
        val deleteButton = view.findViewById<Button>(R.id.btn_delete)

        if (userRole != "admin") {
            deleteButton.visibility = View.GONE
        } else {
            deleteButton.visibility = View.VISIBLE
            deleteButton.setOnClickListener {
                eventId.let { id ->
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            eventDao.deleteById(id)
                        }
                        Toast.makeText(requireContext(), "Event deleted", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }
}
