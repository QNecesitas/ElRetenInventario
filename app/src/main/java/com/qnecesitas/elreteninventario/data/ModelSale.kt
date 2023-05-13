package com.qnecesitas.elreteninventario.data

import java.util.Date

data class ModelSale(
    var c_order: Int,
    var name: String,
    var products: String,
    var totalPrice: Double,
    var totalInv: Double,
    var discount: Double,
    var day: Int,
    var month: Int,
    var year: Int
)