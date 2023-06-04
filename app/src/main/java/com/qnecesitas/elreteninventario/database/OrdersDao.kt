package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query


@Dao
interface OrdersDao {

    @Query("INSERT INTO `Order` (name, products, totalPrice, totalInv,discount,day, month, year, type,totalTransf)VALUES(:name,:products,:totalPrice,:totalInv,:discount,:day,:month,:year,:type,:totalTransf)")
    fun insertOrders(name: String, products: String, totalPrice: Double, totalInv: Double, discount: Double, day: Int, month: Int, year: Int, type: String, totalTransf: Double)

    @Query("DELETE FROM `Order` WHERE c_order = :c_order")
    fun deleteOrders(c_order: String)

}