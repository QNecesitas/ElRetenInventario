package com.qnecesitas.elreteninventario.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("SessionS")
data class ModelSessionS (
    @PrimaryKey(autoGenerate = false) var c_sessionS: String,
    @ColumnInfo(name = "fk_c_drawerS") var fk_c_drawerS: String,
    @ColumnInfo(name = "amount") var amount: Int = 0
)