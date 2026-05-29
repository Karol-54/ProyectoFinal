package com.jaimes.nodocivico.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jaimes.nodocivico.R

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchNotifications = view.findViewById<Switch>(R.id.switchNotifications)
        val switchOfflineMode = view.findViewById<Switch>(R.id.switchOfflineMode)
        val btnSave = view.findViewById<Button>(R.id.btnSaveSettings)

        val prefs = requireContext().getSharedPreferences("nodocivico_prefs", Context.MODE_PRIVATE)
        switchNotifications.isChecked = prefs.getBoolean("notifications", true)
        switchOfflineMode.isChecked = prefs.getBoolean("offline_mode", false)

        btnSave.setOnClickListener {
            prefs.edit()
                .putBoolean("notifications", switchNotifications.isChecked)
                .putBoolean("offline_mode", switchOfflineMode.isChecked)
                .apply()
            Toast.makeText(requireContext(), "Preferencias guardadas", Toast.LENGTH_SHORT).show()
        }
    }
}