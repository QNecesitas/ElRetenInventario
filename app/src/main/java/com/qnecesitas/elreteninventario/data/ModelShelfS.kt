package com.qnecesitas.elreteninventario.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("ShelfS")
data class ModelShelfS(
    @PrimaryKey(autoGenerate = false) var c_shelfS: String,
    @ColumnInfo(name = "amount") var amount: Int,
)

