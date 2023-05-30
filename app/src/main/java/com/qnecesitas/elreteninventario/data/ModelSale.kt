package com.qnecesitas.elreteninventario.data

data class ModelSale(
    var c_order: Int,
    var name: String,
    var products: String,
    var totalPrice: Double,
    var totalInv: Double,
    var discount: Double,
    var day: Int,
    var month: Int,
    var year: Int,
    var type: String,
    var totalTranf: Double
)