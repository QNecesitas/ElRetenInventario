package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query
import com.qnecesitas.elreteninventario.data.ModelPassword


@Dao
interface AccountDao {

    @Query("UPDATE Account SET password=:password WHERE user = :user")
    fun updateAccount(password: String,user: String)


    @Query("SELECT * FROM `Account`")
    fun selectAccount() : ArrayList<ModelPassword>

}