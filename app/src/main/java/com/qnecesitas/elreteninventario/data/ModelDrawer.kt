package com.qnecesitas.elreteninventario.data

data class ModelDrawer(
    var code : String,
    var fk_c_shelfS : String,
    var amount : Int = 0
)