package com.qnecesitas.elreteninventario.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Order")
data class ModelSale(
    @PrimaryKey(autoGenerate = true) var c_order: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "products") var products: String,
    @ColumnInfo(name = "totalPrice") var totalPrice: Double,
    @ColumnInfo(name = "totalInv") var totalInv: Double,
    @ColumnInfo(name = "discount") var discount: Double,
    @ColumnInfo(name = "day") var day: Int,
    @ColumnInfo(name = "month") var month: Int,
    @ColumnInfo(name = "year") var year: Int,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "totalTransf") var totalTransf: Double
)