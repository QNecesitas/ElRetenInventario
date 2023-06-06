package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.qnecesitas.elreteninventario.data.ModelSale
import com.qnecesitas.elreteninventario.data.ModelShelfS


@Dao
interface ShelfSDao {

    @Query("SELECT * FROM ShelfS")
    fun selectShelfS() : ArrayList<ModelShelfS>

    @Query("INSERT INTO ShelfS (c_shelfS)VALUES(:c_shelfS)")
    fun insertShelfS(c_shelfS: String)

    @Query("UPDATE ShelfS SET amount=amount+1 WHERE c_shelfS = :fk_c_shelfS")
    fun updateShelfSmore(fk_c_shelfS: String)

    @Query("UPDATE ShelfS SET amount = amount-1 WHERE c_shelfS = :fk_c_shelfS")
    fun updateShelfSless(fk_c_shelfS: String)

    @Query("DELETE FROM ShelfS WHERE c_shelfS = :c_shelfS")
    fun deleteShelfS(c_shelfS: String)

}