package com.jaimes.nodocivico.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jaimes.nodocivico.databinding.FragmentReportDetailBinding
import com.jaimes.nodocivico.viewmodel.ReportViewModel

class ReportDetailFragment : Fragment() {

    private var _binding: FragmentReportDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by activityViewModels()
    private val args: ReportDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reportId = args.reportId

        viewModel.getReportById(reportId).observe(viewLifecycleOwner) { report ->
            if (report == null) return@observe  // AGREGA ESTA LÍNEA

            binding.tvReportTitle.text = report.title
            binding.tvReportStatus.text = "Estado: ${report.status}"
            binding.tvReportCategory.text = "Categoría: ${report.category}"
            binding.tvReportPriority.text = "Prioridad: ${report.priority}"
            binding.tvReportLocation.text = "Ubicación: ${report.location}"
            binding.tvReportDate.text = "Fecha: ${report.date}"
            binding.tvReportDescription.text = report.description

            binding.btnEditReport.setOnClickListener {
                val action = ReportDetailFragmentDirections
                    .actionReportDetailToEditReport(reportId = report.id)
                findNavController().navigate(action)
            }

            binding.btnDeleteReport.setOnClickListener {
                viewModel.deleteReport(report)
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}