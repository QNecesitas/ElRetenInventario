package com.qnecesitas.elreteninventario.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("ShelfLS")
data class ModelShelfLS(
    @PrimaryKey(autoGenerate = false) var c_shelfLS: String,
    @ColumnInfo(name = "amount", defaultValue = "0") var amount: Int,
)

