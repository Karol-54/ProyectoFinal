package com.jaimes.nodocivico.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaimes.nodocivico.R
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
            adapter.submitList(reportList)
            binding.tvEmptyMessage.visibility =
                if (reportList.isEmpty()) View.VISIBLE else View.GONE
        }

        binding.fabNewReport.setOnClickListener {
            findNavController().navigate(R.id.action_reportList_to_reportDetail)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}