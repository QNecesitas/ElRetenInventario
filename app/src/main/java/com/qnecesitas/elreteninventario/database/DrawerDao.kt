package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.qnecesitas.elreteninventario.data.ModelDrawerLS

@Dao
interface DrawerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDrawer(drawerLS: ModelDrawerLS)




}