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
    fun selectShelfLS() : ArrayList<ModelShelfLS>

    @Query("INSERT INTO ShelfLS (c_shelfLS, amount)VALUES(:c_shelfLS,0)")
    fun insertShelfLS(c_shelfLS: String)

    @Query("UPDATE ShelfLS SET amount=amount+1 WHERE c_shelfLS = :fk_c_shelfLS")
    fun updateShelfLS(fk_c_shelfLS: String)

    @Query("UPDATE ShelfLS SET amount = amount-1 WHERE c_shelfLS = :fk_c_shelfLS")
    fun updateAmountShelfLS(fk_c_shelfLS: String)

    @Query("DELETE FROM ShelfLS WHERE c_shelfLS = :c_shelfLS")
    fun deleteShelfLS(c_shelfLS: String)

    @Query("DELETE FROM ShelfLS WHERE LOWER(c_shelfLS)=LOWER(:c_shelfLSOld)")
    fun deleteShelfLSLower(c_shelfLSOld:String)



}