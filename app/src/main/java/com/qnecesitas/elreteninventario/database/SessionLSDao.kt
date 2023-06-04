package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query


@Dao
interface SessionLSDao {

    @Query("UPDATE SessionLS SET amount = amount -1  WHERE c_sessionLS= :fk_c_sessionLS")
    fun updateSessionLS(fk_c_sessionLS: String)

    @Query("DELETE FROM SessionLS WHERE c_sessionLS = :c_sessionLS")
    fun deleteSessionLS(c_sessionLS: String)

}