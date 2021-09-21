package com.example.obukesingleactivityapplication.ui.activity_history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.obukesingleactivityapplication.databinding.FragmentActivityHistoryBinding
import com.example.obukesingleactivityapplication.models.ActivityItem

class ActivityHistoryFragment: Fragment(), ActivityHistoryAdapter.OnDeleteActivityListener {
    private var _binding: FragmentActivityHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ActivityHistoryViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivityHistoryBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ActivityHistoryViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ActivityHistoryAdapter(requireContext(), this)
        binding.activityHistoryRecycler.adapter = adapter

        viewModel.activityHistoryLiveData.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })
        val sharedPreferences = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val bearerToken = sharedPreferences.getString("BEARER_TOKEN", "")


        viewModel.getAllActivityHistory(bearerToken?: "")
    }

    override fun onDeleteActivity(activityItem: ActivityItem) {
        viewModel.removeActivityFromList(activityItem)
    }
}