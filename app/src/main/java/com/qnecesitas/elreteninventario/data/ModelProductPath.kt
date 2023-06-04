package com.qnecesitas.elreteninventario.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class ModelProductPath (
    var fk_c_shelfS: String,
    var fk_c_drawerS: String
    )