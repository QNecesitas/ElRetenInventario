package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query


@Dao
interface SessionSDao {

    @Query("UPDATE SessionS SET amount = amount + 1 WHERE c_sessionS=:fk_c_sessionS")
    fun updateSessionSmore(fk_c_sessionS: String)

    @Query("UPDATE SessionS SET amount = amount -1  WHERE c_sessionS= :fk_c_sessionS")
    fun updateSessionS(fk_c_sessionS: String)

    @Query("DELETE FROM SessionS WHERE c_sessionS = :c_sessionS")
    fun deleteSessionSless(c_sessionS: String)

}