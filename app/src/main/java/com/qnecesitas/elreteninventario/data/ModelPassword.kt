package com.qnecesitas.elreteninventario.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Account")
data class ModelPassword(
        @ColumnInfo(name = "password") var password: String,
        @PrimaryKey(autoGenerate = false) var user: String
)