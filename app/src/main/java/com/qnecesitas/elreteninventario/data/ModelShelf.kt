package com.qnecesitas.elreteninventario.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class ModelShelf(
    @PrimaryKey(autoGenerate = false) var c_shelfS: String,
    @ColumnInfo(name = "ci") var amount: Int,
)

