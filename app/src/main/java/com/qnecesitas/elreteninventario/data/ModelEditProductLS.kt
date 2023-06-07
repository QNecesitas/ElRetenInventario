package com.qnecesitas.elreteninventario.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("ProductLS")
data class ModelEditProductLS(
        @PrimaryKey(autoGenerate = false) var c_productLS: String,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "fk_c_sessionLS") var fk_c_sessionLS: String,
        @ColumnInfo(name = "amount") var amount: Int,
        @ColumnInfo(name = "buyPrice") var buyPrice: Double,
        @ColumnInfo(name = "salePrice") var salePrice: Double,
        @ColumnInfo(name = "descr") var descr: String,
        @ColumnInfo(name = "statePhoto") var statePhoto: Int,
        @ColumnInfo(name = "deficit") var deficit: Int,
        @ColumnInfo(name = "size") var size: String,
        @ColumnInfo(name = "brand") var brand : String
): Cloneable, ModelProductG{
    public override fun clone(): ModelEditProductLS{
        return super.clone() as ModelEditProductLS
    }
}