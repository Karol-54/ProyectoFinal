package com.jaimes.nodocivico.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.jaimes.nodocivico.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName = view.findViewById<TextInputEditText>(R.id.etProfileName)
        val etEmail = view.findViewById<TextInputEditText>(R.id.etProfileEmail)
        val tvReportsCount = view.findViewById<TextView>(R.id.tvProfileReportsCount)
        val btnSave = view.findViewById<Button>(R.id.btnSaveProfile)

        val prefs = requireContext().getSharedPreferences("nodocivico_prefs", Context.MODE_PRIVATE)
        etName.setText(prefs.getString("user_name", ""))
        etEmail.setText(prefs.getString("user_email", ""))
        tvReportsCount.text = "Reportes creados: ${prefs.getInt("reports_count", 0)}"

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            prefs.edit()
                .putString("user_name", name)
                .putString("user_email", email)
                .apply()
            Toast.makeText(requireContext(), "Perfil guardado", Toast.LENGTH_SHORT).show()
        }
    }
}