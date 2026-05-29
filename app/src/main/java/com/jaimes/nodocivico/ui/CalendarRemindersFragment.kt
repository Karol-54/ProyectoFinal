package com.jaimes.nodocivico.ui

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jaimes.nodocivico.R
import com.jaimes.nodocivico.receivers.ReminderReceiver

class CalendarRemindersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_calendar_reminders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvStatus = view.findViewById<TextView>(R.id.tvReminderStatus)
        val btnSchedule = view.findViewById<Button>(R.id.btnScheduleReminder)
        val btnCancel = view.findViewById<Button>(R.id.btnCancelReminder)

        btnSchedule.setOnClickListener {
            scheduleReminder()
            tvStatus.text = "Recordatorio activo"
            Toast.makeText(requireContext(), "Recordatorio programado", Toast.LENGTH_SHORT).show()
        }

        btnCancel.setOnClickListener {
            cancelReminder()
            tvStatus.text = "Sin recordatorios activos"
            Toast.makeText(requireContext(), "Recordatorio cancelado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun scheduleReminder() {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(), 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val triggerTime = System.currentTimeMillis() + 60 * 1000
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
    }

    private fun cancelReminder() {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(), 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}