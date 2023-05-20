package com.qnecesitas.elreteninventario.data

data class ModelEditProduct(
    var c_productS: String,
    var name: String,
    var fk_c_sessionS: String,
    var amount: Int,
    var buyPrice: Double,
    var salePrice: Double,
    var descr: String,
    var statePhoto: Int,
    var deficit: Int,
    var size: String,
    var brand : String
): Cloneable{
    public override fun clone(): ModelEditProduct{
        return super.clone() as ModelEditProduct
    }
}