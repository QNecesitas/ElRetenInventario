package com.qnecesitas.elreteninventario.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class ModelProductPath (
    @ColumnInfo(name = "ci") var fk_c_shelfS: String,
    @ColumnInfo(name = "month") var fk_c_drawerS: String
    )