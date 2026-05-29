package com.jaimes.nodocivico.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
        val tvConnection = view.findViewById<TextView>(R.id.tvConnectionStatus)
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

        val isConnected = checkConnectivity()
        if (isConnected) {
            tvConnection.text = "Estado: En línea ✓"
            tvConnection.setTextColor(0xFF2E7D32.toInt())
        } else {
            tvConnection.text = "Estado: Sin conexión ✗"
            tvConnection.setTextColor(0xFFE53935.toInt())
        }

        btnSync.setOnClickListener {
            if (isConnected) {
                viewModel.syncReports()
                tvConnection.text = "Estado: Sincronizando..."
                tvConnection.setTextColor(0xFF1565C0.toInt())
            } else {
                tvConnection.text = "Estado: Sin conexión ✗"
                tvConnection.setTextColor(0xFFE53935.toInt())
            }
        }

        viewModel.syncStatus.observe(viewLifecycleOwner) { status ->
            tvConnection.text = "Estado: $status"
            when {
                status.contains("✓") -> tvConnection.setTextColor(0xFF2E7D32.toInt())
                status.contains("✗") -> tvConnection.setTextColor(0xFFE53935.toInt())
                else -> tvConnection.setTextColor(0xFF1565C0.toInt())
            }
        }
    }

    private fun checkConnectivity(): Boolean {
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork
        val capabilities = cm.getNetworkCapabilities(network)
        return capabilities != null &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}