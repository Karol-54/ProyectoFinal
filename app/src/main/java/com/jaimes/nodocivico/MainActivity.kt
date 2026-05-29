package com.jaimes.nodocivico

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jaimes.nodocivico.receivers.ConnectivityReceiver

class MainActivity : AppCompatActivity() {

    private lateinit var connectivityReceiver: ConnectivityReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectivityReceiver = ConnectivityReceiver()
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(connectivityReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(connectivityReceiver)
    }
}