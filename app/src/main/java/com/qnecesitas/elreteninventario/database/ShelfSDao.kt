package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.qnecesitas.elreteninventario.data.ModelSale
import com.qnecesitas.elreteninventario.data.ModelShelfS


@Dao
interface ShelfSDao {

    @Query("SELECT * FROM ShelfS")
    suspend fun selectShelfS() : List<ModelShelfS>

    @Query("INSERT INTO ShelfS (c_shelfS)VALUES(:c_shelfS)")
    suspend fun insertShelfS(c_shelfS: String)

    @Query("UPDATE ShelfS SET amount=amount+1 WHERE c_shelfS = :fk_c_shelfS")
    suspend fun updateShelfSmore(fk_c_shelfS: String)

    @Query("UPDATE ShelfS SET amount = amount-1 WHERE c_shelfS = :fk_c_shelfS")
    suspend fun updateShelfSless(fk_c_shelfS: String)

    @Query("DELETE FROM ShelfS WHERE c_shelfS = :c_shelfS")
    suspend fun deleteShelfS(c_shelfS: String)

}