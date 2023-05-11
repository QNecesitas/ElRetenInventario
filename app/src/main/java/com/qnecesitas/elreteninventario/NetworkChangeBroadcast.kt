package com.qnecesitas.elreteninventario

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast

class NetworkChangeBroadcast : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        val manager : ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo : NetworkInfo? = manager.activeNetworkInfo
        val intentService = Intent(context, DeficitService::class.java)
        if(networkInfo != null && networkInfo.isConnected){
            context.startService(intentService)
        } else {
            context.stopService(intentService)
        }
    }
}