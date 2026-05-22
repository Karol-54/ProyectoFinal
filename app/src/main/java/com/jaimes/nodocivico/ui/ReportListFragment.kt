package com.jaimes.nodocivico.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaimes.nodocivico.databinding.FragmentReportListBinding
import com.jaimes.nodocivico.viewmodel.ReportViewModel

class ReportListFragment : Fragment() {

    private var _binding: FragmentReportListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by activityViewModels()
    private lateinit var adapter: ReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ReportAdapter { report ->
            viewModel.selectReport(report)
            val action = ReportListFragmentDirections
                .actionReportListToReportDetail(reportId = report.id)
            findNavController().navigate(action)
        }

        binding.recyclerViewReports.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewReports.adapter = adapter

        viewModel.reports.observe(viewLifecycleOwner) { reportList ->
            binding.progressBar.visibility = View.GONE

            if (reportList.isEmpty()) {
                binding.tvEmptyMessage.visibility = View.VISIBLE
                binding.recyclerViewReports.visibility = View.GONE
            } else {
                binding.tvEmptyMessage.visibility = View.GONE
                binding.recyclerViewReports.visibility = View.VISIBLE
                adapter.submitList(reportList)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            if (errorMsg != null) {
                binding.progressBar.visibility = View.GONE
                binding.tvErrorMessage.visibility = View.VISIBLE
                binding.recyclerViewReports.visibility = View.GONE
                binding.tvEmptyMessage.visibility = View.GONE
            }
        }

        binding.fabNewReport.setOnClickListener {
            val action = ReportListFragmentDirections
                .actionReportListToCreateReport(reportId = -1)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}