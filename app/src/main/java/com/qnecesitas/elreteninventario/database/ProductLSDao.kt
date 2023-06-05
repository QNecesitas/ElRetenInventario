package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProductLSDao {

    @Query("UPDATE ProductLS SET amount = :amount WHERE c_productLS = :c_productLS")
    fun updateProductLSAmount(amount: Int , c_productLS: String)


    @Query("INSERT INTO ProductLS VALUES (:c_productS,:name,:fk_c_sessionS,:amount,:buyPrice,:salePrice,:descr, :statePhoto,:deficit,:size,:brand)")
    fun insertProductLS(c_productS: String,name: String, fk_c_sessionS: String,amount: Int,buyPrice: Double,salePrice: Double, descr: String, statePhoto: Int, deficit: Int, size: String, brand: String)

    @Query("DELETE FROM ProductLS WHERE c_productLS= :c_productLS")
    fun deleteProductLS(c_productLS: String)

    @Query("UPDATE ProductLS SET fk_c_sessionLS=:c_sessionLSNew WHERE fk_c_sessionLS=:c_sessionLSOld")
    fun updateProductLSforain(c_sessionLSNew:String,c_sessionLSOld:String)

    @Query("UPDATE ProductLS SET c_productLS=:c_productLS ,name=:name,amount=:amount, buyPrice=:buyPrice, salePrice=:salePrice,descr=:descr, statePhoto=:statePhoto, deficit = :deficit, size=:size,brand=:brand WHERE c_productLS=:c_productLSOld")
    fun updateProductLS(c_productLS:String,name:String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,statePhoto: Int,deficit: Int,size: String,brand: String,c_productLSOld:String)

}