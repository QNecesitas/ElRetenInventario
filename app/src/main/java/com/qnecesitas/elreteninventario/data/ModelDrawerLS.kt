package com.qnecesitas.elreteninventario.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("DrawerLS")
data class ModelDrawerLS (
    @PrimaryKey(autoGenerate = false) var c_drawerLS: String,
    @ColumnInfo(name = "fk_c_shelfLS") var fk_c_shelfLS: String,
    @ColumnInfo(name = "amount") var amount: Int = 0
)