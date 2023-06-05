package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query


@Dao
interface SessionSDao {

    @Query("UPDATE SessionS SET fk_c_drawerS=:c_drawerSNew WHERE fk_c_drawerS=:c_drawerSOld")
    fun updateSessionSUp(c_drawerSNew: String,c_drawerSOld: String)

    @Query("UPDATE SessionS SET amount = amount-1 WHERE c_sessionS=:fk_c_sessionS")
    fun updateSessionSTransLess(fk_c_sessionS: String)

    @Query("UPDATE SessionS SET amount = amount+1 WHERE c_sessionS = :c_sessionS")
    fun updateSessionSTransMore(c_sessionS: String)

    @Query("SELECT * FROM SessionS WHERE fk_c_drawerS = :fk_c_drawerS")
    fun selectSessionS(fk_c_drawerS: String)

    @Query("INSERT INTO SessionS VALUES (:c_sessionS,:fk_c_drawerS,0)")
    fun insertSessionS(c_sessionS: String,fk_c_drawerS: String)

    @Query("UPDATE SessionS SET amount = amount + 1 WHERE c_sessionS=:fk_c_sessionS")
    fun updateSessionSmore(fk_c_sessionS: String)

    @Query("UPDATE SessionS SET amount = amount -1  WHERE c_sessionS= :fk_c_sessionS")
    fun updateSessionS(fk_c_sessionS: String)

    @Query("DELETE FROM SessionS WHERE c_sessionS = :c_sessionS")
    fun deleteSessionS(c_sessionS: String)

}