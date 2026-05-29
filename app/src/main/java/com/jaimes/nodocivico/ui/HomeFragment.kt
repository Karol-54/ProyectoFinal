package com.jaimes.nodocivico.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
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
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

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
        viewModel.resolvedReports.observe(viewLifecycleOwner) {
            binding.tvResolvedReports.text = it.toString()
        }

        setupConnectivityIndicator()

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

    private fun setupConnectivityIndicator() {
        connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        checkConnectivity()

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                activity?.runOnUiThread { updateConnectivityUI(true) }
            }
            override fun onLost(network: Network) {
                activity?.runOnUiThread { updateConnectivityUI(false) }
            }
            override fun onCapabilitiesChanged(network: Network, caps: NetworkCapabilities) {
                val connected = caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                activity?.runOnUiThread { updateConnectivityUI(connected) }
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    private fun checkConnectivity() {
        val network = connectivityManager.activeNetwork
        val connected = connectivityManager.getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        updateConnectivityUI(connected)
    }

    private fun updateConnectivityUI(isConnected: Boolean) {
        if (isConnected) {
            binding.tvConnectivityStatus.visibility = View.GONE
        } else {
            binding.tvConnectivityStatus.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        connectivityManager.unregisterNetworkCallback(networkCallback)
        _binding = null
    }
}