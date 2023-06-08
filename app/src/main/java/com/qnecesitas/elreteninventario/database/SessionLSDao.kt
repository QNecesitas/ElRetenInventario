package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query
import com.qnecesitas.elreteninventario.data.ModelSale
import com.qnecesitas.elreteninventario.data.ModelSessionLS
import com.qnecesitas.elreteninventario.data.ModelSessionS


@Dao
interface SessionLSDao {

    @Query("UPDATE SessionLS SET fk_c_drawerLS=:c_drawerLSNew WHERE fk_c_drawerLS=:c_drawerLSOld")
    suspend fun updateSessionLSUp(c_drawerLSNew: String,c_drawerLSOld: String)

    @Query("UPDATE SessionLS SET amount = amount+1 WHERE c_sessionLS = :c_sessionLS")
    suspend fun updateSessionLSTransMore(c_sessionLS: String)

    @Query("UPDATE SessionLS SET amount = amount-1 WHERE c_sessionLS=:fk_c_sessionLS")
    suspend fun updateSessionLSTransLess(fk_c_sessionLS: String)

    @Query("SELECT * FROM SessionLS WHERE fk_c_drawerLS = :fk_c_drawerLS")
    suspend fun selectSessionLS(fk_c_drawerLS: String) : List<ModelSessionLS>

    @Query("INSERT INTO SessionLS VALUES (:c_sessionLS,:fk_c_drawerLS,0)")
    suspend fun insertSessionLS(c_sessionLS: String,fk_c_drawerLS: String)

    @Query("UPDATE SessionLS SET amount = amount + 1 WHERE c_sessionLS=:fk_c_sessionS")
    suspend fun updateSessionLSmore(fk_c_sessionS: String)


    @Query("UPDATE SessionLS SET amount = amount -1  WHERE c_sessionLS= :fk_c_sessionLS")
    suspend fun updateSessionLSless(fk_c_sessionLS: String)

    @Query("DELETE FROM SessionLS WHERE c_sessionLS = :c_sessionLS")
    suspend fun deleteSessionLS(c_sessionLS: String)

    @Query("INSERT INTO SessionLS VALUES (:c_sessionLSNew,:fk_c_drawerLS,:amount)")
    suspend fun insertSessionLSAmount(c_sessionLSNew:String,fk_c_drawerLS:String,amount:Int)

}