package com.qnecesitas.elreteninventario.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("ProductS")
data class ModelEditProductS(
        @PrimaryKey(autoGenerate = false) var c_productS: String,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "fk_c_sessionS") var fk_c_sessionS: String,
        @ColumnInfo(name = "amount") var amount: Int,
        @ColumnInfo(name = "buyPrice") var buyPrice: Double,
        @ColumnInfo(name = "salePrice") var salePrice: Double,
        @ColumnInfo(name = "descr") var descr: String,
        @ColumnInfo(name = "statePhoto") var statePhoto: Int,
        @ColumnInfo(name = "deficit") var deficit: Int,
        @ColumnInfo(name = "size") var size: String,
        @ColumnInfo(name = "brand") var brand : String
): Cloneable{
    public override fun clone(): ModelEditProductS{
        return super.clone() as ModelEditProductS
    }
}