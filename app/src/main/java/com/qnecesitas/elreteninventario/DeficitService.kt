package com.qnecesitas.elreteninventario

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class DeficitService : Service() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service start", Toast.LENGTH_SHORT).show()
        return START_STICKY
    }

    override fun onDestroy() {
        Toast.makeText(this, "Service done", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}