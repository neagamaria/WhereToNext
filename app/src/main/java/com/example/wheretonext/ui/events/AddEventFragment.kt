package com.example.wheretonext.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.wheretonext.R
import com.example.wheretonext.daos.EventDAO
import com.example.wheretonext.data.AppDatabase
import com.example.wheretonext.data.models.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class AddEventFragment : Fragment() {

    private lateinit var eventDao: EventDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.event_form, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize DAO (adjust to your app's database setup)
        val db = AppDatabase.getDatabase(requireContext())
        eventDao = db.eventDAO

        val nameInput = view.findViewById<EditText>(R.id.nameInput)
        val locationIdInput = view.findViewById<EditText>(R.id.locationIdInput)
        val dateInput = view.findViewById<EditText>(R.id.dateInput)
        val durationInput = view.findViewById<EditText>(R.id.durationInput)
        val categoryIdInput = view.findViewById<EditText>(R.id.categoryIdInput)
        val descriptionInput = view.findViewById<EditText>(R.id.descriptionInput)
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            // generate id
            val id = UUID.randomUUID().toString()

            val name = nameInput.text.toString().trim()
            val locationId = locationIdInput.text.toString().trim()
            val dateStr = dateInput.text.toString().trim()
            val durationStr = durationInput.text.toString().trim()
            val categoryId = categoryIdInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()

            if (name.isEmpty() || locationId.isEmpty() || dateStr.isEmpty() ||
                durationStr.isEmpty() || categoryId.isEmpty() || description.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Parse date (assumes format yyyy-MM-dd)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date: Date? = try {
                sdf.parse(dateStr)
            } catch (e: ParseException) {
                null
            }

            if (date == null) {
                Toast.makeText(requireContext(), "Invalid date format. Use yyyy-MM-dd", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val duration: Int = try {
                durationStr.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(requireContext(), "Duration must be a number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val event = Event(
                id = id,
                name = name,
                locationId = locationId,
                date = date,
                duration = duration,
                categoryId = categoryId,
                description = description
            )

            // Insert event in background thread
            lifecycleScope.launch {
                eventDao.insert(event)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Event added", Toast.LENGTH_SHORT).show()
                    clearForm(nameInput, locationIdInput, dateInput, durationInput, categoryIdInput, descriptionInput)
                }
            }
        }
    }

    private fun clearForm(vararg inputs: EditText) {
        for (input in inputs) {
            input.text.clear()
        }
    }
}
