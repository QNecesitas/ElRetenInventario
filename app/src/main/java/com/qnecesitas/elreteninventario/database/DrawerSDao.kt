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

    @Query("DELETE FROM DrawerS WHERE c_drawerS =:c_drawerSOld")
    suspend fun deleteDrawerSUp(c_drawerSOld: String)

    @Query("INSERT INTO DrawerS VALUES (:c_drawerSNew,:fk_c_shelfS,:amount)")
    suspend fun insertDrawerSUp(c_drawerSNew: String,fk_c_shelfS: String,amount: Int)

    @Query("SELECT * FROM DrawerS WHERE fk_c_shelfS = :fk_c_shelfS")
    suspend fun selectDrawerS(fk_c_shelfS: String) : List<ModelDrawerS>

    @Query("UPDATE DrawerS SET amount = amount+1 WHERE c_drawerS = :fk_c_drawerS")
    suspend fun updateDrawerSmore(fk_c_drawerS: String)

    @Query("INSERT INTO DrawerS VALUES (:c_drawerS,:fk_c_shelfS,0)")
    suspend fun insertDawerS(c_drawerS: String, fk_c_shelfS: String)

    @Query("DELETE FROM DrawerS WHERE c_drawerS = :c_drawerS")
    suspend fun deleteDrawerS(c_drawerS: String)

    @Query("UPDATE DrawerS SET amount = amount-1 WHERE c_drawerS = :fk_c_drawerS")
    suspend fun updateDrawerSless(fk_c_drawerS: String)

    @Query("UPDATE DrawerS SET fk_c_shelfS=:c_shelfSNew WHERE fk_c_shelfS=:c_shelfSOld")
    suspend fun updateDrawerSforain(c_shelfSNew:String,c_shelfSOld:String)

}