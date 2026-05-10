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
            report?.let {
                binding.tvReportTitle.text = it.title
                binding.tvReportStatus.text = "Estado: ${it.status}"
                binding.tvReportCategory.text = "Categoría: ${it.category}"
                binding.tvReportPriority.text = "Prioridad: ${it.priority}"
                binding.tvReportLocation.text = "Ubicación: ${it.location}"
                binding.tvReportDate.text = "Fecha: ${it.date}"
                binding.tvReportDescription.text = it.description

                binding.btnEditReport.setOnClickListener { _ ->
                    val action = ReportDetailFragmentDirections
                        .actionReportDetailToCreateReport(reportId = it.id)
                    findNavController().navigate(action)
                }

                binding.btnDeleteReport.setOnClickListener { _ ->
                    viewModel.deleteReport(it)
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}