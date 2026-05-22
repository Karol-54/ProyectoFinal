package com.jaimes.nodocivico.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jaimes.nodocivico.databinding.FragmentCreateReportBinding
import com.jaimes.nodocivico.model.Report
import com.jaimes.nodocivico.viewmodel.ReportViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditReportFragment : Fragment() {

    private var _binding: FragmentCreateReportBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by activityViewModels()
    private val args: EditReportFragmentArgs by navArgs()
    private var currentReport: Report? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvFormTitle.text = "Editar Reporte"

        viewModel.getReportById(args.reportId).observe(viewLifecycleOwner) { report ->
            if (report == null) return@observe
            currentReport = report
            binding.etTitle.setText(report.title)
            binding.etDescription.setText(report.description)
            binding.etCategory.setText(report.category)
            binding.etPriority.setText(report.priority)
            binding.etLocation.setText(report.location)
        }

        binding.btnSave.setOnClickListener { saveReport() }
        binding.btnSaveOffline.setOnClickListener {
            saveReport()
            Toast.makeText(requireContext(), "Guardado offline", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveReport() {
        val title = binding.etTitle.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()
        val category = binding.etCategory.text.toString().trim()
        val priority = binding.etPriority.text.toString().trim()
        val location = binding.etLocation.text.toString().trim()

        if (title.isEmpty() || description.isEmpty() ||
            category.isEmpty() || priority.isEmpty() || location.isEmpty()) {
            Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val report = currentReport?.copy(
            title = title,
            description = description,
            category = category,
            priority = priority,
            location = location
        ) ?: return

        viewModel.updateReport(report)
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}