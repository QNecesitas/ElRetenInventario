package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.qnecesitas.elreteninventario.data.ModelShelfLS


@Dao
interface ShelfLSDao {


    @Query("UPDATE ShelfLS SET amount=amount+1 WHERE c_shelfLS = :fk_c_shelfLS")
    fun updateShelfLS(fk_c_shelfLS: String)

    @Query("UPDATE ShelfLS SET amount = amount-1 WHERE c_shelfLS = :fk_c_shelfLS")
    fun updateAmountShelfLS(fk_c_shelfLS: String)

    @Query("DELETE FROM ShelfLS WHERE c_shelfLS = :c_shelfLS")
    fun deleteShelfLS(c_shelfLS: String)



}