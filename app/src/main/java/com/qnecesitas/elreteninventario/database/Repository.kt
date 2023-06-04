package com.qnecesitas.elreteninventario.database

import android.app.Application
import com.qnecesitas.elreteninventario.ElRetenApplication

class Repository(private val application: Application) {
    val drawerLSDao = (application as ElRetenApplication).database.drawerLSDao()
    val shelfSLSDao = (application as ElRetenApplication).database.shelfLSDao()
    val drawerSDao = (application as ElRetenApplication).database.drawerSDao()
    val shelfSSDao = (application as ElRetenApplication).database.shelfSDao()
    val ordersDao = (application as ElRetenApplication).database.ordersDao()
    val sessionS = (application as ElRetenApplication).database.sessionS()
    val sessionLS = (application as ElRetenApplication).database.sessionLS()
    val productS = (application as ElRetenApplication).database.productS()
    val productLS = (application as ElRetenApplication).database.productLS()

    fun addDrawerLs(c_drawerLS: String, fk_c_shelfLS: String, ){
         drawerLSDao.insertDrawerLS(c_drawerLS,fk_c_shelfLS)
         shelfSLSDao.updateShelfLS(fk_c_shelfLS)
    }

    fun addDrawerS(c_drawerS: String, fk_c_shelfS: String){
        drawerSDao.insertDawerS(c_drawerS, fk_c_shelfS)
        shelfSSDao.updateShelfSmore(fk_c_shelfS)
    }

    fun addOrder(name: String, products: String, totalPrice: Double, totalInv: Double, discount: Double, type: String, totalTransf: Double, day: Int, month: Int, year: Int ){
        ordersDao.insertOrders(name,products,totalPrice,totalInv,discount,day,month,year,type,totalTransf)
    }

    fun addProduct(c_productS: String, name: String, fk_c_sessionS: String, amount: Double, buyPrice: Double, salePrice: Double, descr: String, file: String, deficit: Int, statePhoto: Int, size: String, brand: String){
        productS.insertProducts(c_productS,name,fk_c_sessionS,amount,buyPrice,salePrice,descr,statePhoto,deficit,size,brand)
        sessionS.updateSessionSmore(fk_c_sessionS)
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
        drawerLSDao.deleteDrawerLS(c_drawerLS)
        shelfSLSDao.updateAmountShelfLS(fk_c_shelfLS)
    }

    fun deleteDrawerS(c_drawerS: String,fk_c_shelfS: String){
        drawerSDao.deleteDrawerS(c_drawerS)
        shelfSSDao.updateShelfSless(fk_c_shelfS)
    }

    fun deleteOrder(c_order: String){
        ordersDao.deleteOrders(c_order)
    }

    fun deleteProduct(c_productS: String,fk_c_sessionS: String){
        sessionS.updateSessionS(fk_c_sessionS)
        productS.deleteProductS(c_productS)
    }

    fun deleteProductLS(c_productLS: String,fk_c_sessionS: String){
        productLS.deleteProductLS(c_productLS)
        sessionLS.updateSessionLS(fk_c_sessionS)
    }

    fun deleteSessionLS(c_sessionLS: String,fk_c_drawerLS: String){
        sessionLS.deleteSessionLS(c_sessionLS)
        drawerLSDao.updateDrawerLS(fk_c_drawerLS)
    }


    fun deleteSessionS(c_sessionS: String,fk_c_drawerS: String){
        sessionS.deleteSessionSless(c_sessionS)
        drawerSDao.updateDrawerS(fk_c_drawerS)
    }


    fun deleteShelfLS(c_shelfLS: String){
        shelfSLSDao.deleteShelfLS(c_shelfLS)
    }


    fun deleteShelfS(c_shelfS: String){
        shelfSSDao.deleteShelfS(c_shelfS)
    }


}