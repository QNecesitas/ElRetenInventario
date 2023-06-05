package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProductLSDao {

    @Query("UPDATE ProductLS SET amount = :amount WHERE c_productLS = :c_productLS")
    fun updateProductLS(amount: Int, c_productLS: String)


    @Query("INSERT INTO ProductLS VALUES (:c_productS,:name,:fk_c_sessionS,:amount,:buyPrice,:salePrice,:descr, :statePhoto,:deficit,:size,:brand)")
    fun insertProductLS(c_productS: String,name: String, fk_c_sessionS: String,amount: Int,buyPrice: Double,salePrice: Double, descr: String, statePhoto: Int, deficit: Int, size: String, brand: String)

    @Query("DELETE FROM ProductLS WHERE c_productLS= :c_productLS")
    fun deleteProductLS(c_productLS: String)

}