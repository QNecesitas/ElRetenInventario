package com.qnecesitas.elreteninventario.data

data class ModelDrawer(
    var c_drawerS : String,
    var fk_c_shelfS : String,
    var amount : Int = 0
)