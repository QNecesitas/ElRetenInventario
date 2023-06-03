package com.qnecesitas.elreteninventario.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelPassword(
    @PrimaryKey(autoGenerate = false) var password: String,
    @ColumnInfo(name = "ci") var user: String
)