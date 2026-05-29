package com.jaimes.nodocivico.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jaimes.nodocivico.R
import com.jaimes.nodocivico.viewmodel.ReportViewModel

class MapOrZoneFragment : Fragment() {

    private val viewModel: ReportViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_map_zone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvZoneInfo = view.findViewById<TextView>(R.id.tvZoneInfo)
        val tvReportsList = view.findViewById<TextView>(R.id.tvZoneReports)

        viewModel.reports.observe(viewLifecycleOwner) { reports ->
            if (reports.isEmpty()) {
                tvZoneInfo.text = "No hay reportes en tu zona"
                tvReportsList.text = ""
            } else {
                tvZoneInfo.text = "Reportes en tu zona: ${reports.size}"
                tvReportsList.text = reports.joinToString("\n") {
                    "• ${it.title} - ${it.location}"
                }
            }
        }
    }
}