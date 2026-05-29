package com.jaimes.nodocivico.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jaimes.nodocivico.R
import com.jaimes.nodocivico.viewmodel.ReportViewModel

class SyncStatusFragment : Fragment() {

    private val viewModel: ReportViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_sync_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTotal = view.findViewById<TextView>(R.id.tvTotalReports)
        val tvPending = view.findViewById<TextView>(R.id.tvPendingSync)
        val tvSynced = view.findViewById<TextView>(R.id.tvSyncedReports)
        val btnSync = view.findViewById<Button>(R.id.btnSyncNow)

        viewModel.totalReports.observe(viewLifecycleOwner) {
            tvTotal.text = "Total reportes: $it"
        }

        viewModel.pendingReports.observe(viewLifecycleOwner) {
            tvPending.text = "Pendientes: $it"
        }

        viewModel.syncedReports.observe(viewLifecycleOwner) {
            tvSynced.text = "Sincronizados: $it"
        }

        btnSync.setOnClickListener {
            viewModel.syncReports()
        }
    }
}