package com.qnecesitas.elreteninventario

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BroadcastRestartService : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val intentService : Intent = Intent(context, DeficitService::class.java)
        context?.startService(intentService)
    }
}