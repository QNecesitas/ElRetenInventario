package com.qnecesitas.elreteninventario.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("SessionLS")
data class ModelSessionLS (
    @PrimaryKey(autoGenerate = false) var c_sessionLS: String,
    @ColumnInfo(name = "fk_c_drawerLS") var fk_c_drawerLS: String,
    @ColumnInfo(name = "amount") var amount: Int = 0
)