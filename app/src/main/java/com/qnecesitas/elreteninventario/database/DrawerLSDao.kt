package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.qnecesitas.elreteninventario.data.ModelDrawerLS

@Dao
interface DrawerLSDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDrawer(drawer: ModelDrawerLS)



}