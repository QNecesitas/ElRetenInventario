package com.qnecesitas.elreteninventario

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast

class ServiceExample : IntentService("ServiceExample") {

    private lateinit var manager : ConnectivityManager
    private var networkInfo : NetworkInfo? = null

    override fun onCreate() {
        super.onCreate()
        manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkInfo = manager.activeNetworkInfo
    }

    override fun onHandleIntent(p0: Intent?) {
        while (true){
            if (isConnected()){
                Toast.makeText(this,"Conx detected, starting actions",Toast.LENGTH_SHORT).show()
            }
            Thread.sleep(30000)
        }
    }

    private fun isConnected() : Boolean{
        return networkInfo!=null && networkInfo!!.isConnected
    }
}