package com.jaimes.nodocivico.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast

class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork
        val capabilities = cm.getNetworkCapabilities(network)
        val isConnected = capabilities != null &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

        if (isConnected) {
            Toast.makeText(context, "Conexión restaurada. Sincronizando...", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Sin conexión. Modo offline activo.", Toast.LENGTH_SHORT).show()
        }
    }
}