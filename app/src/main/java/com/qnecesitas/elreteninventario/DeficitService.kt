package com.qnecesitas.elreteninventario

import android.app.IntentService
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.widget.Toast

class DeficitService() : IntentService("DEFICIT_SERVICE_THREAD"){

    private lateinit var manager : ConnectivityManager
    private var networkInfo: NetworkInfo? = null

    override fun onCreate() {
        super.onCreate()
        manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkInfo = manager.activeNetworkInfo
    }

    override fun onHandleIntent(intent : Intent?) {
        Toast.makeText(this,"Service START",Toast.LENGTH_SHORT).show()
    }

    private fun isConnected() : Boolean{
        return networkInfo != null && networkInfo!!.isConnected
    }
}