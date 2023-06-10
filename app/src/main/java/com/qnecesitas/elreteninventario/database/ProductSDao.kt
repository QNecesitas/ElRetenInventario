package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query
import com.qnecesitas.elreteninventario.data.ModelEditProductLS
import com.qnecesitas.elreteninventario.data.ModelEditProductS
import com.qnecesitas.elreteninventario.data.ModelProductPath
import com.qnecesitas.elreteninventario.data.ModelSale


@Dao
interface ProductSDao {

    @Query("UPDATE ProductS SET amount = amount-:amount WHERE c_productS = :c_productS")
    suspend fun updateProductSTransLess(amount: Int,c_productS: String)


    @Query("DELETE FROM ProductS WHERE c_productS =:c_productS")
    suspend fun deleteProductSTrans(c_productS: String)


    @Query("INSERT INTO ProductS VALUES (:c_productLS,:name,:c_sessionS,:amount,:buyPrice,:salePrice,:descr,:statePhoto,:deficit,:size,:brand)")
    suspend fun insertProductSTrans(c_productLS: String,name: String,c_sessionS: String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,statePhoto: Int,deficit: Int,size: String,brand: String)

    @Query("UPDATE ProductS SET amount = amount+:amount WHERE c_productS = :c_productLS")
    suspend fun updateProductSTransMore(amount: Int,c_productLS: String)


    @Query("SELECT * FROM ProductS ORDER BY name ASC")
    suspend fun selectProductSAll() : List<ModelEditProductS>


    @Query("SELECT * FROM ProductS WHERE fk_c_sessionS= :fk_c_sessionS ORDER BY name ASC")
    suspend fun selectProductS(fk_c_sessionS: String) : List<ModelEditProductS>


    @Query("SELECT * FROM ProductS WHERE amount <= deficit ORDER BY name ASC")
    suspend fun selectProductSDeficit() : List<ModelEditProductS>


    @Query("SELECT * FROM products ORDER BY name ASC")
    suspend fun selectProdcutSCounter() : List<ModelEditProductS>


   @Query("SELECT ShelfS.c_shelfS, DrawerS.c_drawerS FROM ProductS JOIN SessionS ON ProductS.fk_c_sessionS = SessionS.c_sessionS JOIN DrawerS ON SessionS.fk_c_drawerS = DrawerS.c_drawerS JOIN ShelfS ON DrawerS.fk_c_shelfS = ShelfS.c_shelfS WHERE ProductS.c_productS = :c_productS")
   suspend fun selectProductLSPath(c_productS: String): List<ModelProductPath>


    @Query("UPDATE ProductS SET amount = :amount WHERE c_productS = :c_productS")
    suspend fun updateProductAmountS(amount: Int , c_productS: String)

    @Query("INSERT INTO ProductS VALUES (:c_productS,:name,:fk_c_sessionS,:amount,:buyPrice,:salePrice,:descr, :statePhoto,:deficit,:size,:brand)")
    suspend fun insertProducts(c_productS: String, name: String , fk_c_sessionS: String, amount: Int, buyPrice: Double, salePrice: Double, descr: String, statePhoto: Int, deficit: Int, size: String, brand: String)

    @Query("DELETE FROM ProductS WHERE c_productS= :c_productS")
    suspend fun deleteProductS(c_productS: String)

    @Query("UPDATE ProductS SET  name=:name,amount=:amount, buyPrice=:buyPrice, salePrice=:salePrice,descr=:descr, statePhoto=:statePhoto, deficit = :deficit, size=:size,brand=:brand WHERE c_productS=:c_productS")
    suspend fun updateProductS(name:String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,statePhoto: Int,deficit: Int,size: String,brand: String,c_productS: String)

    @Query("SELECT * FROM ProductS WHERE c_productS = :c_productS")
    suspend fun selectDuplicatedProducts(c_productS: String): List<ModelEditProductS>


}