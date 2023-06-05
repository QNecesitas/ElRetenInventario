package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query


@Dao
interface ProductSDao {

    @Query("UPDATE ProductS SET amount = amount-:amount WHERE c_productS = :c_productS")
    fun updateProductSTransLess(amount: Int,c_productS: String)


    @Query("DELETE FROM ProductS WHERE c_productS =:c_productS")
    fun deleteProductSTrans(c_productS: String)


    @Query("INSERT INTO ProductS VALUES (:c_productLS,:name,:c_sessionS,:amount,:buyPrice,:salePrice,:descr,:statePhoto,:deficit,:size,:brand)")
    fun insertProductSTrans(c_productLS: String,name: String,c_sessionS: String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,statePhoto: Int,deficit: Int,size: String,brand: String)

    @Query("UPDATE ProductS SET amount = amount+:amount WHERE c_productS = :c_productLS")
    fun updateProductSTransMore(amount: Int,c_productLS: String)


    @Query("SELECT * FROM ProductS ORDER BY name ASC")
    fun selectProductSAll()


    @Query("SELECT * FROM ProductS WHERE fk_c_sessionS= :fk_c_sessionS ORDER BY name ASC")
    fun selectProductS(fk_c_sessionS: String)


    @Query("SELECT * FROM ProductS WHERE amount <= deficit ORDER BY name ASC")
    fun selectProductSDeficit()


    @Query("SELECT * FROM products ORDER BY name ASC")
    fun selectProdcutSCounter()


    @Query("SELECT ShelfS.c_shelfS, DrawerS.c_drawerS FROM ProductS JOIN SessionS ON ProductS.fk_c_sessionS = SessionS.c_sessionS JOIN DrawerS ON SessionS.fk_c_drawerS = DrawerS.c_drawerS JOIN ShelfS ON DrawerS.fk_c_shelfS = ShelfS.c_shelfS WHERE ProductS.c_productS = :c_productS")
    fun selectProductLSPath(c_productS: String)


    @Query("UPDATE ProductS SET amount = :amount WHERE c_productS = :c_productS")
    fun updateProductS(amount: Int, c_productS: String)

    @Query("INSERT INTO ProductS VALUES (:c_productS,:name,:fk_c_sessionS,:amount,:buyPrice,:salePrice,:descr, :statePhoto,:deficit,:size,:brand)")
    fun insertProducts(c_productS: String, name: String , fk_c_sessionS: String, amount: Double, buyPrice: Double, salePrice: Double, descr: String, statePhoto: Int, deficit: Int, size: String, brand: String)

    @Query("DELETE FROM ProductS WHERE c_productS= :c_productS")
    fun deleteProductS(c_productS: String)

}