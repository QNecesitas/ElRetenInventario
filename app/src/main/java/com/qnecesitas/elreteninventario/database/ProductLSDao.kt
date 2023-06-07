package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query
import com.qnecesitas.elreteninventario.data.ModelEditProductLS
import com.qnecesitas.elreteninventario.data.ModelEditProductS

@Dao
interface ProductLSDao {

    @Query("INSERT INTO ProductLS VALUES (:c_productS,:name,:c_sessionLS,:amount,:buyPrice,:salePrice,:descr,:statePhoto,:deficit,:size,:brand)")
    fun insertProductLSTrans(c_productS: String,name: String,c_sessionLS: String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,statePhoto: Int,deficit: Int,size: String,brand: String)


    @Query("UPDATE ProductLS SET amount = amount-:amount WHERE c_productLS = :c_productLS")
    fun updateProductLSTrans(amount: Int,c_productLS: String)


    @Query("UPDATE ProductLS SET amount = amount+:amount WHERE c_productLS = :c_productS")
    fun updateProductSTrans(amount: Int,c_productS: String)


    @Query("DELETE FROM ProductLS WHERE c_productLS =:c_productLS")
    fun deleteProductLSTrans(c_productLS: String)


    @Query("SELECT * FROM ProductLS ORDER BY name ASC")
    fun selectProductLSAll() : List<ModelEditProductLS>


    @Query("SELECT * FROM ProductLS WHERE fk_c_sessionLS = :fk_c_sessionLS ORDER BY name ASC")
    fun selectProductLS(fk_c_sessionLS: String) : List<ModelEditProductLS>


    //@Query("SELECT * FROM ProductLS WHERE amount <= deficit")
    //fun selectProductLSDeficit() : List<ModelEditProductS>


    //@Query("SELECT ShelfLS.c_shelfLS, DrawerLS.c_drawerLS FROM ProductLS JOIN SessionLS ON ProductLS.fk_c_sessionLS = SessionLS.c_sessionLS JOIN DrawerLS ON SessionLS.fk_c_drawerLS = DrawerLS.c_drawerLS JOIN ShelfLS ON DrawerLS.fk_c_shelfLS = ShelfLS.c_shelfLS WHERE ProductLS.c_productLS = :c_productLS")
    //fun selectProductLSPath(c_productLS: String) : List<ModelEditProductLS>


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