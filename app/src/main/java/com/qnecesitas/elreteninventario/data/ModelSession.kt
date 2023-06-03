package com.qnecesitas.elreteninventario.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelSession (
    @PrimaryKey(autoGenerate = false) var c_sessionS: String,
    @ColumnInfo(name = "ci") var fk_c_drawerS: String,
    @ColumnInfo(name = "month") var amount: Int = 0
)