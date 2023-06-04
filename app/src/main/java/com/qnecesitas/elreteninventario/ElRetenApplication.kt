package com.qnecesitas.elreteninventario

import android.app.Application
import com.qnecesitas.elreteninventario.database.AppDatabase

class ElRetenApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}