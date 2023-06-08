package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.qnecesitas.elreteninventario.data.ModelSale
import com.qnecesitas.elreteninventario.data.ModelShelfLS
import com.qnecesitas.elreteninventario.data.ModelShelfS


@Dao
interface ShelfLSDao {

    @Query("SELECT * FROM ShelfLS")
    suspend fun selectShelfLS() : List<ModelShelfLS>

    @Query("INSERT INTO ShelfLS (c_shelfLS, amount)VALUES(:c_shelfLS,0)")
    suspend fun insertShelfLS(c_shelfLS: String)

    @Query("UPDATE ShelfLS SET amount=amount+1 WHERE c_shelfLS = :fk_c_shelfLS")
    suspend fun updateShelfLS(fk_c_shelfLS: String)

    @Query("UPDATE ShelfLS SET amount = amount-1 WHERE c_shelfLS = :fk_c_shelfLS")
    suspend fun updateAmountShelfLS(fk_c_shelfLS: String)

    @Query("DELETE FROM ShelfLS WHERE c_shelfLS = :c_shelfLS")
    suspend fun deleteShelfLS(c_shelfLS: String)

    @Query("DELETE FROM ShelfLS WHERE LOWER(c_shelfLS)=LOWER(:c_shelfLSOld)")
    suspend fun deleteShelfLSLower(c_shelfLSOld:String)



}