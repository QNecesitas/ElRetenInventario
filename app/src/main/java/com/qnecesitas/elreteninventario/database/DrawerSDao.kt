package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qnecesitas.elreteninventario.data.ModelDrawerLS
import com.qnecesitas.elreteninventario.data.ModelDrawerS

@Dao
interface DrawerSDao {

    @Query("INSERT INTO DrawerS VALUES (:c_drawerS,:fk_c_shelfS,0)")
    fun insertDawerS(c_drawerS: String, fk_c_shelfS: String)

    @Query("DELETE FROM DrawerS WHERE c_drawerS = :c_drawerS")
    fun deleteDrawerS(c_drawerS: String)

    @Query("UPDATE DrawerS SET amount = amount-1 WHERE c_drawerS = :fk_c_drawerS")
    fun updateDrawerS(fk_c_drawerS: String)

}