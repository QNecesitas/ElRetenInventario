package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query


@Dao
interface SessionLSDao {

    @Query("INSERT INTO SessionLS VALUES (:c_sessionLS,:fk_c_drawerLS,0)")
    fun insertSessionLS(c_sessionLS: String,fk_c_drawerLS: String)

    @Query("UPDATE SessionLS SET amount = amount + 1 WHERE c_sessionLS=:fk_c_sessionS")
    fun updateSessionLSmore(fk_c_sessionS: String)


    @Query("UPDATE SessionLS SET amount = amount -1  WHERE c_sessionLS= :fk_c_sessionLS")
    fun updateSessionLSless(fk_c_sessionLS: String)

    @Query("DELETE FROM SessionLS WHERE c_sessionLS = :c_sessionLS")
    fun deleteSessionLS(c_sessionLS: String)

}