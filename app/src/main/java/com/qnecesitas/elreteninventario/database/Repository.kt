package com.qnecesitas.elreteninventario.database

import android.app.Application
import com.qnecesitas.elreteninventario.ElRetenApplication
import com.qnecesitas.elreteninventario.data.ModelDrawerLS
import com.qnecesitas.elreteninventario.data.ModelDrawerS
import com.qnecesitas.elreteninventario.data.ModelEditProductLS
import com.qnecesitas.elreteninventario.data.ModelEditProductS
import com.qnecesitas.elreteninventario.data.ModelPassword
import com.qnecesitas.elreteninventario.data.ModelProductPath
import com.qnecesitas.elreteninventario.data.ModelSale
import com.qnecesitas.elreteninventario.data.ModelSessionLS
import com.qnecesitas.elreteninventario.data.ModelSessionS
import com.qnecesitas.elreteninventario.data.ModelShelfLS
import com.qnecesitas.elreteninventario.data.ModelShelfS

class Repository(private val application: Application) {
    val drawerLSDao = (application as ElRetenApplication).database.drawerLSDao()
    val shelfSLSDao = (application as ElRetenApplication).database.shelfLSDao()
    val drawerSDao = (application as ElRetenApplication).database.drawerSDao()
    val shelfSSDao = (application as ElRetenApplication).database.shelfSDao()
    val ordersDao = (application as ElRetenApplication).database.ordersDao()
    val sessionSDao = (application as ElRetenApplication).database.sessionSDao()
    val sessionLSDao = (application as ElRetenApplication).database.sessionLSDao()
    val productSDao = (application as ElRetenApplication).database.productSDao()
    val productLSDao = (application as ElRetenApplication).database.productLSDao()
    val accountDao = (application as ElRetenApplication).database.accountDao()

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

    fun addProduct(c_productS: String, name: String, fk_c_sessionS: String, amount: Int, buyPrice: Double, salePrice: Double, descr: String, file: String, deficit: Int, size: String, brand: String){
        productSDao.insertProducts(c_productS,name,fk_c_sessionS,amount,buyPrice,salePrice,descr,1,deficit,size,brand)
        sessionSDao.updateSessionSmore(fk_c_sessionS)
    }

    fun addProductLS(c_productS: String, name: String, fk_c_sessionS: String, amount: Int, buyPrice: Double, salePrice: Double, descr: String, file: String, deficit: Int, size: String, brand: String){
        productLSDao.insertProductLS(c_productS,name,fk_c_sessionS,amount,buyPrice,salePrice,descr,deficit,1,size,brand)
        sessionLSDao.updateSessionLSmore(fk_c_sessionS)
    }

    fun addSessionLS(c_sessionLS: String, fk_c_drawerLS: String){
        sessionLSDao.insertSessionLS(c_sessionLS,fk_c_drawerLS)
       drawerLSDao.updateDrawerLSmore(fk_c_drawerLS)
    }

    fun addSessionS(c_sessionS: String, fk_c_drawerS: String){
        sessionSDao.insertSessionS(c_sessionS,fk_c_drawerS)
        drawerSDao.updateDrawerSmore(fk_c_drawerS)
    }

    fun addShelfLS(c_shelfLS: String){
        shelfSLSDao.insertShelfLS(c_shelfLS)
    }

    fun addShelfS(c_shelfS: String){
        shelfSSDao.insertShelfS(c_shelfS)
    }

    fun alterAmountLS(c_productLS: String, amount: Int){
        productLSDao.updateProductLSAmount(amount,c_productLS)
    }

    fun alterAmountS(c_productS: String, amount: Int){
        productSDao.updateProductAmountS(amount,c_productS)
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
        sessionSDao.updateSessionS(fk_c_sessionS)
        productSDao.deleteProductS(c_productS)
    }

    fun deleteProductLS(c_productLS: String,fk_c_sessionS: String){
        productLSDao.deleteProductLS(c_productLS)
        sessionLSDao.updateSessionLSless(fk_c_sessionS)
    }

    fun deleteSessionLS(c_sessionLS: String,fk_c_drawerLS: String){
        sessionLSDao.deleteSessionLS(c_sessionLS)
        drawerLSDao.updateDrawerLSless(fk_c_drawerLS)
    }


    fun deleteSessionS(c_sessionS: String,fk_c_drawerS: String){
        sessionSDao.deleteSessionS(c_sessionS)
        drawerSDao.updateDrawerSless(fk_c_drawerS)
    }


    fun deleteShelfLS(c_shelfLS: String){
        shelfSLSDao.deleteShelfLS(c_shelfLS)
    }


    fun deleteShelfS(c_shelfS: String){
        shelfSSDao.deleteShelfS(c_shelfS)
    }

    fun fetchAccounts() : ArrayList<ModelPassword>{
        return accountDao.selectAccount()
    }

    fun fetchDrawersLS(fk_c_shelfLS: String) :ArrayList<ModelDrawerLS>{
        return drawerLSDao.selectDrawerLS(fk_c_shelfLS)
    }

    fun fetchDrawersS(fk_c_shelfS: String) : ArrayList<ModelDrawerS>{
        return drawerSDao.selectDrawerS(fk_c_shelfS)
    }

    fun fetchOrdersAll() : ArrayList<ModelSale>{
        return ordersDao.selectOrderAll()
    }

    fun fetchOrdersD(year:Int,month: Int,day: Int) : ArrayList<ModelSale>{
        return ordersDao.selectOrderD(day,month,year)
    }

    fun fetchOrdersM(year: Int,month: Int) : ArrayList<ModelSale>{
        return ordersDao.selectOrdersM(month,year)
    }

    fun fetchOrdersY(year: Int) : ArrayList<ModelSale>{
        return ordersDao.selectOrderY(year)
    }

    fun fetchProductLSPath(c_productLS: String) : ArrayList<ModelProductPath>{
        return productLSDao.selectProductLSPath(c_productLS)
    }

    fun fetchProductSPath(c_productS: String): ArrayList<ModelProductPath>{
        return productSDao.selectProductLSPath(c_productS)
    }

    fun fetchProductsCounter() : ArrayList<ModelEditProductS>{
        return productSDao.selectProdcutSCounter()
    }

    fun fetchProductsDeficit(button:String) : ArrayList<ModelEditProductS>{
        if(button == "Almacén"){
            return productSDao.selectProductSDeficit()
        }else{
            return productLSDao.selectProductLSDeficit()
        }
    }

    fun fetchProductsLS(fk_c_sessionLS: String) : ArrayList<ModelEditProductLS>{
        return productLSDao.selectProductLS(fk_c_sessionLS)
    }

    fun fetchProductsLSAll() : ArrayList<ModelEditProductLS>{
        return productLSDao.selectProductLSAll()
    }

    fun fetchProductsS(fk_c_sessionS:String) : ArrayList<ModelEditProductS>{
        return productSDao.selectProductS(fk_c_sessionS)
    }

    fun fetchProductsSAll() : ArrayList<ModelEditProductS> {
        val result = productSDao.selectProductSAll()
        for (it in productLSDao.selectProductLSAll()){
            val newModel = ModelEditProductS(
                it.c_productLS,
                it.name,
                it.fk_c_sessionLS,
                it.amount,
                it.buyPrice,
                it.salePrice,
                it.descr,
                it.statePhoto,
                it.deficit,
                it.size,
                it.brand
            )
            result.add( newModel)
        }

        return result
    }

    fun fetchSessionsLS(fk_c_drawerLS: String) : ArrayList<ModelSessionLS>{
        return sessionLSDao.selectSessionLS(fk_c_drawerLS)
    }

    fun fetchSessionsS(fk_c_drawerS: String): ArrayList<ModelSessionS>{
        return sessionSDao.selectSessionS(fk_c_drawerS)
    }

    fun fetchShelvesLS(): ArrayList<ModelShelfLS>{
        return shelfSLSDao.selectShelfLS()
    }

    fun fetchShelvesS() : ArrayList<ModelShelfS>{
        return shelfSSDao.selectShelfS()
    }

    fun transferProductLS_S(c_productLS: String,name: String,fk_c_sessionLS: String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,statePhoto: Int,c_sessionS: String,deficit: Int,exists:Int,sendAll: Int,size: String,brand: String){
        if(exists == 1){
            productSDao.updateProductSTransMore(amount,c_productLS)
        }else{
            productSDao.insertProductSTrans(c_productLS,name,c_sessionS,amount,buyPrice,salePrice,descr,statePhoto,deficit,size,brand)
            sessionSDao.updateSessionSTransMore(c_sessionS)
        }
        if(sendAll == 1){
            sessionLSDao.updateSessionLSTransLess(fk_c_sessionLS)
            productLSDao.deleteProductLSTrans(c_productLS)
        }else{
            productLSDao.updateProductLSTrans(amount,c_productLS)
        }
    }

    fun transferProductS_LS(c_productS: String,name: String,fk_c_sessionS: String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,statePhoto: Int,c_sessionLS: String,deficit: Int,exists: Int,sendAll: Int,size: String,brand: String){
        if(exists == 1){
            productLSDao.updateProductSTrans(amount,c_productS)
        }else{
            productLSDao.insertProductLSTrans(c_productS,name,c_sessionLS,amount,buyPrice,salePrice,descr,statePhoto,deficit,size,brand)
            sessionLSDao.updateSessionLSTransMore(c_sessionLS)
        }
        if(sendAll == 1){
            sessionSDao.updateSessionSTransLess(fk_c_sessionS)
            productSDao.deleteProductSTrans(c_productS)
        }else{
            productSDao.updateProductSTransLess(amount,c_productS)
        }
    }

    fun updateAccount(password:String,user:String){
        accountDao.updateAccount(password,user)
    }

    fun updateDrawerLS(c_drawerLSOld: String,c_drawerLSNew:String,fk_c_shelfLS: String,amount: Int){
        drawerLSDao.insertDrawerLSUp(c_drawerLSNew,fk_c_shelfLS,amount)
        sessionLSDao.updateSessionLSUp(c_drawerLSNew,c_drawerLSOld)
        drawerLSDao.deleteDrawerLSUp(c_drawerLSOld)
    }

    fun updateDrawerS(c_drawerSOld: String,c_drawerSNew: String,fk_c_shelfS: String,amount: Int){
        drawerSDao.insertDrawerSUp(c_drawerSNew,fk_c_shelfS,amount)
        sessionSDao.updateSessionSUp(c_drawerSNew,c_drawerSOld)
        drawerSDao.deleteDrawerSUp(c_drawerSOld)
    }

    fun updateProduct(file: String,c_productS: String,name: String,fk_c_sessionS: String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,deficit: Int,statePhoto: Int,size: String,brand: String){
        productSDao.updateProductS(name,amount,buyPrice,salePrice,descr,statePhoto,deficit,size,brand,c_productS)
    }

    fun updateProductLS(c_productLSOld: String,file: String,c_productLS: String,name: String,fk_c_sessionLS: String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,deficit: Int,size: String,brand: String){
        productLSDao.updateProductLS(c_productLS,name,amount,buyPrice,salePrice,descr,1,deficit,size,brand,c_productLSOld)

    }

    fun updateSessionLS(c_sessionLSOld: String,c_sessionLSNew: String,fk_c_drawerLS: String,amount: Int){
        sessionLSDao.insertSessionLSAmount(c_sessionLSNew,fk_c_drawerLS,amount)
        productLSDao.updateProductLSforain(c_sessionLSNew,c_sessionLSOld)
        sessionLSDao.deleteSessionLS(c_sessionLSOld)




    }

    fun updateSessionS(c_sessionSOld: String,c_sessionSNew: String,fk_c_drawerS: String,amount: Int){
        sessionSDao.insertSessionSAmount(c_sessionSNew,fk_c_drawerS,amount)
        sessionSDao.updateProductSforain(c_sessionSNew,c_sessionSOld)
        sessionSDao.deleteSessionS(c_sessionSOld)
    }

    fun updateShelfLS(c_shelfLSOld: String,c_shelfLSNew: String,amount: Int){
        shelfSLSDao.insertShelfLS(c_shelfLSNew)
        drawerLSDao.updateDrawerLSForain(c_shelfLSNew,c_shelfLSOld)
        shelfSLSDao.deleteShelfLSLower(c_shelfLSOld)

    }

    fun updateShelfS(c_shelfSOld: String,c_shelfSNew: String,amount: Int){
        shelfSSDao.insertShelfS(c_shelfSOld)
        drawerSDao.updateDrawerSforain(c_shelfSNew,c_shelfSOld)
        shelfSSDao.deleteShelfS(c_shelfSOld)
    }




}