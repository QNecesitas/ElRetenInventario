package com.qnecesitas.elreteninventario.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("DrawerS")
data class ModelDrawerS(
    @PrimaryKey(autoGenerate = false) var c_drawerS: String,
    @ColumnInfo(name = "fk_c_shelfS") var fk_c_shelfS: String,
    @ColumnInfo(name = "amount", defaultValue = "0") var amount: Int ,
)


