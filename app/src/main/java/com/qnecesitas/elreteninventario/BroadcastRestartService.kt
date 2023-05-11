package com.qnecesitas.elreteninventario

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BroadcastRestartService : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val restartService = Intent(context,ServiceExample::class.java)
        context!!.startService(restartService)
    }
}