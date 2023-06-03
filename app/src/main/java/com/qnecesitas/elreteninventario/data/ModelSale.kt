package com.qnecesitas.elreteninventario.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class ModelSale(
    @PrimaryKey(autoGenerate = true) var c_order: Int,
    @ColumnInfo(name = "ci") var name: String,
    @ColumnInfo(name = "month") var products: String,
    @ColumnInfo(name = "ci") var totalPrice: Double,
    @ColumnInfo(name = "month") var totalInv: Double,
    @ColumnInfo(name = "ci") var discount: Double,
    @ColumnInfo(name = "month") var day: Int,
    @ColumnInfo(name = "ci") var month: Int,
    @ColumnInfo(name = "ci") var year: Int,
    @ColumnInfo(name = "month") var type: String,
    @ColumnInfo(name = "month") var totalTranf: Double
)