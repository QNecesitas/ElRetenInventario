package com.qnecesitas.elreteninventario.database

import androidx.room.Dao
import androidx.room.Query
import com.qnecesitas.elreteninventario.data.ModelSale


@Dao
interface OrdersDao {

    @Query("SELECT * FROM `order` WHERE  year=:year ORDER BY month ASC")
    fun selectOrderY(year: Int) : List<ModelSale>

    @Query("SELECT * FROM `order` WHERE month=:month AND year=:year ORDER BY day ASC")
    fun selectOrdersM(month: Int,year: Int) : List<ModelSale>

    @Query("SELECT * FROM `order` WHERE day = :day AND month=:month AND year=:year ")
    fun selectOrderD(day: Int, month: Int, year: Int) : List<ModelSale>

    @Query("SELECT * FROM `Order` ORDER BY year ASC")
    fun selectOrderAll() : List<ModelSale>

    @Query("INSERT INTO `Order` (name, products, totalPrice, totalInv,discount,day, month, year, type,totalTransf)VALUES(:name,:products,:totalPrice,:totalInv,:discount,:day,:month,:year,:type,:totalTransf)")
    fun insertOrders(name: String, products: String, totalPrice: Double, totalInv: Double, discount: Double, day: Int, month: Int, year: Int, type: String, totalTransf: Double)

    @Query("DELETE FROM `Order` WHERE c_order = :c_order")
    fun deleteOrders(c_order: Int)

}