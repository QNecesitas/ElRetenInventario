package com.qnecesitas.elreteninventario.database

import androidx.room.Query
import androidx.room.Transaction

interface DrawerLSDao {

    @Query("INSERT INTO ModelDrawerLS VALUES (:c_drawerLSNew,:fk_c_shelfLS,:amount)")
    fun insertDrawerLs(c_drawerLSNew: String,fk_c_shelfLS: String,amount: Int)
    @Query("Update SessionLS Set fk_c_drawerLS = :c_drawerLSNew where fk_c_drawerLS = :c_drawerLSOld")
    fun updateDrawerLs(c_drawerLSNew: String,c_drawerLSOld: String)
    @Query("Delete From DrawerLS where c_drawerLS = :c_drawerLSOld")
    fun deleteDrawerLs(c_drawerLSOld: String)

    @Transaction
    fun updateDrawer(
        c_drawerLSOld : String,
        c_drawerLSNew : String,
        fk_c_shelfS: String,
        amount: Int
    ): String{
        val drawer = insertDrawerLs(c_drawerLSNew,fk_c_shelfS,amount).let {
            updateDrawerLs(c_drawerLSNew,c_drawerLSOld)
            deleteDrawerLs(c_drawerLSOld)
        }
    }



}