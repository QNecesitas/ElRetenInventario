package com.qnecesitas.elreteninventario.database

import android.app.Application
import com.qnecesitas.elreteninventario.ElRetenApplication

class Repository(private val application: Application) {
    val drawerLSDao = (application as ElRetenApplication).database.drawerLSDao()


    fun addDrawerLs(c_drawerLS: String, fk_c_shelfLS: String, ){
         //TODO -- INSERT drawerLS
         //TODO -- UPDATE ShelfLS
    }

    fun addDrawerS(c_drawerS: String, fk_c_shelfS: String){
        //TODO -- INSERT drawerS
        //TODO -- UPDATE ShelfS
    }

    fun addOrder(name: String, products: String, totalPrice: Double, totalInv: Double, discount: Double, type: String, totalTransf: Int, day: Int, month: Int, Year: Int ){
        //TODO --Insert OrderS
    }

    fun addProduct(c_productS: String, name: String, fk_c_sessionS: String, amount: Int, buyPrice: Double, salePrice: Double, descr: String, file: String, deficit: Int, statePhoto: Int, size: String, brand: String){
        //TODO --Insert image
        //TODO --Insert into productS
        //TODO --UPDATE sessionS
    }

    fun addProductLS(c_productS: String, name: String, fk_c_sessionS: String, amount: Int, buyPrice: Double, salePrice: Double, descr: String, file: String, deficit: Int, statePhoto: Int, size: String, brand: String){
        //TODO --Insert image
        //TODO --Insert into productLS
        //TODO --UPDATE sessionLS
    }

    fun addSessionLS(c_sessionLS: String, fk_c_drawerLS: String){
        //TODO -- Insert SessionS
        //TODO Update DrawerLS
    }

    fun addSessionS(c_sessionS: String, fk_c_drawerS: String){
        //TODO -- Insert SessionS
        //TODO -- Update DrawerS
    }

    fun addShelfLS(c_shelfLS: String){
        //TODO Insert in ShelfLS
    }

    fun addShelfS(c_shelfS: String){
        //TODO Insert in ShelfS
    }

    fun alterAmountLS(c_productLS: String, amount: Int){
        //TODO Update ProductLS
    }

    fun alterAmountS(c_productS: String, amount: Int){
        //TODO Update ProductS
    }

    fun deleteDrawerLS(c_drawerLS: String,fk_c_shelfLS: String){
        //TODO Delete DrawerLS
    }

    fun deleteDrawerS(c_drawerS: String,fk_c_shelfS: String){
        //TODO Delete DrawerS
    }

    fun deleteOrder(c_order: String){
        //TODO Delete Orders
    }

    fun deleteProduct(c_productS: String){
        //TODO Delete Product
    }

    fun deleteProductLS(c_productLS: String,fk_c_sessionS: String){
        //TODO Delete ProductLS
    }

    fun deleteSessionLS(c_sessionLS: String,fk_c_drawerLS: String){
        //TODO Delete SessionLS
    }


    fun deleteSessionS(c_sessionS: String,fk_c_drawerS: String){
        //TODO Delete SessionS
    }


    fun deleteShelfLS(c_shelfLS: String){
        //TODO Delete ShelfLS
    }


    fun deleteShelfS(c_shelfS: String){
        //TODO Delete ShelfS
    }



}