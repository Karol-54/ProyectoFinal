package com.jaimes.nodocivico.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jaimes.nodocivico.R
import com.jaimes.nodocivico.databinding.FragmentHomeBinding
import com.jaimes.nodocivico.viewmodel.ReportViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.totalReports.observe(viewLifecycleOwner) {
            binding.tvTotalReports.text = it.toString()
        }

        viewModel.pendingReports.observe(viewLifecycleOwner) {
            binding.tvPendingReports.text = it.toString()
        }

        viewModel.syncedReports.observe(viewLifecycleOwner) {
            binding.tvSyncedReports.text = it.toString()
        }

        binding.btnNewReport.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_createReport)
        }

        binding.btnViewReports.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_reportList)
        }

        binding.btnGoSync.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_syncStatus)
        }

        binding.btnGoProfile.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_profile)
        }

        binding.btnGoCalendar.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_calendar)
        }

        binding.btnGoMap.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_map)
        }

        binding.btnGoSettings.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_settings)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}