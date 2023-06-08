package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.qnecesitas.elreteninventario.data.ModelDrawerLS
import com.qnecesitas.elreteninventario.data.ModelPassword

@Dao
interface DrawerLSDao {

    @Query("DELETE FROM DrawerLS WHERE c_drawerLS =:c_drawerLSOld")
    suspend fun deleteDrawerLSUp(c_drawerLSOld: String)

    @Query("INSERT INTO DrawerLS VALUES (:c_drawerLSNew,:fk_c_shelfLS,:amount)")
    suspend fun insertDrawerLSUp(c_drawerLSNew: String,fk_c_shelfLS: String,amount: Int)

    @Query("SELECT * FROM DrawerLS WHERE fk_c_shelfLS = :fk_c_shelfLS")
    suspend fun selectDrawerLS(fk_c_shelfLS: String): List<ModelDrawerLS>

    @Query("UPDATE DrawerLS SET amount = amount+1 WHERE c_drawerLS = :fk_c_drawerLS")
    suspend fun updateDrawerLSmore(fk_c_drawerLS: String)

    @Query("INSERT INTO DrawerLS VALUES (:c_drawerLS,:fk_c_shelfLS,0)")
    suspend fun insertDrawerLS(c_drawerLS: String, fk_c_shelfLS: String)

    @Query("DELETE FROM DrawerLS WHERE c_drawerLS = :c_drawerLS")
    suspend fun deleteDrawerLS(c_drawerLS: String)

    @Query("UPDATE DrawerLS SET amount = amount-1 WHERE c_drawerLS = :fk_c_drawerLS")
    suspend fun updateDrawerLSless(fk_c_drawerLS: String)

    @Query("UPDATE DrawerLS SET fk_c_shelfLS=:c_shelfLSNew WHERE fk_c_shelfLS=:c_shelfLSOld")
    suspend fun updateDrawerLSForain(c_shelfLSNew:String,c_shelfLSOld:String)


}