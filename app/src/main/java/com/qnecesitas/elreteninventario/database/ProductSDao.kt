package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query


@Dao
interface ProductSDao {

    @Query("UPDATE ProductS SET amount = :amount WHERE c_productS = :c_productS")
    fun updateProductS(amount: Int, c_productS: String)

    @Query("INSERT INTO ProductS VALUES (:c_productS,:name,:fk_c_sessionS,:amount,:buyPrice,:salePrice,:descr, :statePhoto,:deficit,:size,:brand)")
    fun insertProducts(c_productS: String, name: String , fk_c_sessionS: String, amount: Double, buyPrice: Double, salePrice: Double, descr: String, statePhoto: Int, deficit: Int, size: String, brand: String)

    @Query("DELETE FROM ProductS WHERE c_productS= :c_productS")
    fun deleteProductS(c_productS: String)

}