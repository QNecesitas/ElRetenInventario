package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProductLSDao {

    @Query("DELETE FROM ProductLS WHERE c_productLS= :c_productLS")
    fun deleteProductLS(c_productLS: String)

}