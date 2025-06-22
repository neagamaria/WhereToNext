package com.example.wheretonext.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretonext.R
import com.example.wheretonext.adapters.EventsAdapter
import com.example.wheretonext.models.Event
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val  recyclerView = view.findViewById<RecyclerView>(R.id.rv_items)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val items = listOf(
            Event("1", "StreetFood", "1", "01.07.2025".toDate(), 8, "1", "desc"),
            Event("1", "OperaFest", "1", "12.07.2025".toDate(), 5, "2", "desc"),
            Event("1", "TheaterPlay", "1", "10.07.2025".toDate(), 2, "3", "desc")
        )
        val adapter = EventsAdapter(items)

        val layoutManager = LinearLayoutManager(requireContext())

        recyclerView.apply {
            this.layoutManager = layoutManager
            this.adapter = adapter
        }
    }


    fun String.toDate(): Date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(this)!!

}