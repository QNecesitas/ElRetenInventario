package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.qnecesitas.elreteninventario.data.ModelDrawerLS

@Dao
interface DrawerLSDao {

    @Query("UPDATE DrawerLS SET amount = amount+1 WHERE c_drawerLS = :fk_c_drawerLS")
    fun updateDrawerLSmore(fk_c_drawerLS: String)

    @Query("INSERT INTO DrawerLS VALUES (:c_drawerLS,:fk_c_shelfLS,0)")
    fun insertDrawerLS(c_drawerLS: String, fk_c_shelfLS: String)

    @Query("DELETE FROM DrawerLS WHERE c_drawerLS = :c_drawerLS")
    fun deleteDrawerLS(c_drawerLS: String)

    @Query("UPDATE DrawerLS SET amount = amount-1 WHERE c_drawerLS = :fk_c_drawerLS")
    fun updateDrawerLSless(fk_c_drawerLS: String)

    @Query("UPDATE DrawerLS SET fk_c_shelfLS=:c_shelfLSNew WHERE fk_c_shelfLS=:c_shelfLSOld")
    fun updateDrawerLSForain(c_shelfLSNew:String,c_shelfLSOld:String)


}