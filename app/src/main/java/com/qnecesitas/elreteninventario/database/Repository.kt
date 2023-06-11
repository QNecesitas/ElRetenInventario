package com.qnecesitas.elreteninventario.database

import android.app.Application
import android.util.Log
import com.qnecesitas.elreteninventario.ElRetenApplication
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelDrawerLS
import com.qnecesitas.elreteninventario.data.ModelDrawerS
import com.qnecesitas.elreteninventario.data.ModelEditProductLS
import com.qnecesitas.elreteninventario.data.ModelEditProductS
import com.qnecesitas.elreteninventario.data.ModelPassword
import com.qnecesitas.elreteninventario.data.ModelProductPath
import com.qnecesitas.elreteninventario.data.ModelProductPathLS
import com.qnecesitas.elreteninventario.data.ModelSale
import com.qnecesitas.elreteninventario.data.ModelSessionLS
import com.qnecesitas.elreteninventario.data.ModelSessionS
import com.qnecesitas.elreteninventario.data.ModelShelfLS
import com.qnecesitas.elreteninventario.data.ModelShelfS

class Repository(application: ElRetenApplication) {
    val drawerLSDao = application.database.drawerLSDao()
    val shelfSLSDao = application.database.shelfLSDao()
    val drawerSDao = application.database.drawerSDao()
    val shelfSSDao = application.database.shelfSDao()
    val ordersDao = application.database.ordersDao()
    val sessionSDao = application.database.sessionSDao()
    val sessionLSDao = application.database.sessionLSDao()
    val productSDao = application.database.productSDao()
    val productLSDao = application.database.productLSDao()
    val accountDao = application.database.accountDao()

    suspend fun addDrawerLs(c_drawerLS: String, fk_c_shelfLS: String, ){
         drawerLSDao.insertDrawerLS(c_drawerLS,fk_c_shelfLS)
         shelfSLSDao.updateShelfLS(fk_c_shelfLS)
    }

    suspend fun addDrawerS(c_drawerS: String, fk_c_shelfS: String){
        drawerSDao.insertDawerS(c_drawerS, fk_c_shelfS)
        shelfSSDao.updateShelfSmore(fk_c_shelfS)
    }

    suspend fun addOrder(name: String, products: String, totalPrice: Double, totalInv: Double, discount: Double, type: String, totalTransf: Double, day: Int, month: Int, year: Int ){
        ordersDao.insertOrders(name,products,totalPrice,totalInv,discount,day,month,year,type,totalTransf)
    }

    suspend fun addProduct(c_productS: String, name: String, fk_c_sessionS: String, amount: Int, buyPrice: Double, salePrice: Double, descr: String, deficit: Int, size: String, brand: String, statePhoto: Int){
        productSDao.insertProducts(c_productS,name,fk_c_sessionS,amount,buyPrice,salePrice,descr,statePhoto,deficit,size,brand)
        sessionSDao.updateSessionSmore(fk_c_sessionS)
    }

    suspend fun addProductLS(c_productS: String, name: String, fk_c_sessionS: String, amount: Int, buyPrice: Double, salePrice: Double, descr: String, deficit: Int, size: String, brand: String){
        productLSDao.insertProductLS(c_productS,name,fk_c_sessionS,amount,buyPrice,salePrice,descr,deficit,deficit,size,brand)
        sessionLSDao.updateSessionLSmore(fk_c_sessionS)
    }

    suspend fun addSessionLS(c_sessionLS: String, fk_c_drawerLS: String){
        sessionLSDao.insertSessionLS(c_sessionLS,fk_c_drawerLS)
       drawerLSDao.updateDrawerLSmore(fk_c_drawerLS)
    }

    suspend fun addSessionS(c_sessionS: String, fk_c_drawerS: String){
        sessionSDao.insertSessionS(c_sessionS,fk_c_drawerS)
        drawerSDao.updateDrawerSmore(fk_c_drawerS)
    }

    suspend fun addShelfLS(c_shelfLS: String){
        shelfSLSDao.insertShelfLS(c_shelfLS)
    }

    suspend fun addShelfS(c_shelfS: String){
        shelfSSDao.insertShelfS(c_shelfS)
    }

    suspend fun alterAmountLS(c_productLS: String, amount: Int){
        productLSDao.updateProductLSAmount(amount,c_productLS)
    }

    suspend fun alterAmountS(c_productS: String, amount: Int){
        productSDao.updateProductAmountS(amount,c_productS)
    }

    suspend fun deleteDrawerLS(c_drawerLS: String,fk_c_shelfLS: String){
        drawerLSDao.deleteDrawerLS(c_drawerLS)
        shelfSLSDao.updateAmountShelfLS(fk_c_shelfLS)
    }

    suspend fun deleteDrawerS(c_drawerS: String,fk_c_shelfS: String){
        drawerSDao.deleteDrawerS(c_drawerS)
        shelfSSDao.updateShelfSless(fk_c_shelfS)
    }

    suspend fun deleteOrder(c_order: Int){
        ordersDao.deleteOrders(c_order)
    }

    suspend fun deleteProduct(c_productS: String,fk_c_sessionS: String){
        sessionSDao.updateSessionS(fk_c_sessionS)
        productSDao.deleteProductS(c_productS)
    }

    suspend fun deleteProductLS(c_productLS: String,fk_c_sessionS: String){
        productLSDao.deleteProductLS(c_productLS)
        sessionLSDao.updateSessionLSless(fk_c_sessionS)
    }

    suspend fun deleteSessionLS(c_sessionLS: String,fk_c_drawerLS: String){
        sessionLSDao.deleteSessionLS(c_sessionLS)
        drawerLSDao.updateDrawerLSless(fk_c_drawerLS)
    }


    suspend fun deleteSessionS(c_sessionS: String,fk_c_drawerS: String){
        sessionSDao.deleteSessionS(c_sessionS)
        drawerSDao.updateDrawerSless(fk_c_drawerS)
    }


    suspend fun deleteShelfLS(c_shelfLS: String){
        shelfSLSDao.deleteShelfLS(c_shelfLS)
    }


    suspend fun deleteShelfS(c_shelfS: String){
        shelfSSDao.deleteShelfS(c_shelfS)
    }

    suspend fun fetchAccounts() : MutableList<ModelPassword>{
        return accountDao.selectAccount().toMutableList()
    }

    suspend fun fetchDrawersLS(fk_c_shelfLS: String) :MutableList<ModelDrawerLS>{
        return drawerLSDao.selectDrawerLS(fk_c_shelfLS).toMutableList()
    }

    suspend fun fetchDrawersS(fk_c_shelfS: String) : MutableList<ModelDrawerS>{
        return drawerSDao.selectDrawerS(fk_c_shelfS).toMutableList()
    }

    suspend fun fetchOrdersAll() : MutableList<ModelSale>{
        return ordersDao.selectOrderAll().toMutableList()
    }

    suspend fun fetchOrdersD(year:Int,month: Int,day: Int) : MutableList<ModelSale>{
        return ordersDao.selectOrderD(day,month,year).toMutableList()
    }

    suspend fun fetchOrdersM(year: Int,month: Int) : MutableList<ModelSale>{
        return ordersDao.selectOrdersM(month,year).toMutableList()
    }

    suspend fun fetchOrdersY(year: Int) : MutableList<ModelSale>{
        return ordersDao.selectOrderY(year).toMutableList()
    }

    suspend fun fetchProductLSPath(c_productLS: String) : MutableList<ModelProductPathLS>{
        return productLSDao.selectProductLSPath(c_productLS).toMutableList()
    }

    suspend fun fetchProductSPath(c_productS: String): MutableList<ModelProductPath>{
        return productSDao.selectProductLSPath(c_productS).toMutableList()
    }

    suspend fun fetchProductsCounter() : MutableList<ModelEditProductLS>{
        return productSDao.selectProdcutSCounter().toMutableList()
    }

    suspend fun fetchProductsDeficit(button:String) : MutableList<ModelEditProductS>{
        if(button == "Almacén"){
            return productSDao.selectProductSDeficit().toMutableList()
        }else{
            val modelImp = productLSDao.selectProductLSDeficit()
            val modelExp = mutableListOf<ModelEditProductS>()
            for (product in modelImp){
                modelExp.add(ModelEditProductS(
                        product.c_productLS,
                        product.name,
                        product.fk_c_sessionLS,
                        product.amount,
                        product.buyPrice,
                        product.salePrice,
                        product.descr,
                        product.statePhoto,
                        product.deficit,
                        product.size,
                        product.brand
                ))
            }
            return modelExp
        }
    }

    suspend fun fetchProductsLS(fk_c_sessionLS: String) : MutableList<ModelEditProductLS>{
        return productLSDao.selectProductLS(fk_c_sessionLS).toMutableList()
    }

    suspend fun fetchProductsLSAll() : MutableList<ModelEditProductLS>{
        return productLSDao.selectProductLSAll().toMutableList()
    }

    suspend fun fetchProductsS(fk_c_sessionS:String) : MutableList<ModelEditProductS>{
        return productSDao.selectProductS(fk_c_sessionS).toMutableList()
    }

    suspend fun fetchProductsSAll() : MutableList<ModelEditProductS> {
        val result = productSDao.selectProductSAll().toMutableList()
        for (it in productLSDao.selectProductLSAll()) {
            val newModel = ModelEditProductS(
                it.c_productLS,
                it.name,
                Constants.KEY_SALESPERSON_PRODUCT+it.fk_c_sessionLS,
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
        result.sortBy { it.name }
        return result
    }

    suspend fun fetchSessionsLS(fk_c_drawerLS: String) : MutableList<ModelSessionLS>{
        return sessionLSDao.selectSessionLS(fk_c_drawerLS).toMutableList()
    }

    suspend fun fetchSessionsS(fk_c_drawerS: String): MutableList<ModelSessionS>{
        return sessionSDao.selectSessionS(fk_c_drawerS).toMutableList()
    }

    suspend fun fetchShelvesLS(): MutableList<ModelShelfLS>{
        return shelfSLSDao.selectShelfLS().toMutableList()
    }

    suspend fun fetchShelvesS() : MutableList<ModelShelfS>{
        return shelfSSDao.selectShelfS().toMutableList()
    }

    suspend fun transferProductLS_S(c_productLS: String,name: String,fk_c_sessionLS: String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,statePhoto: Int,c_sessionS: String,deficit: Int,exists:Boolean,sendAll: Boolean,size: String,brand: String){
        Log.d("XXXXXXXXXXX", "e: ${exists}  sa: ${sendAll} am: ${amount}")
        if(exists){
            productSDao.updateProductSTransMore(amount,c_productLS)
        }else{
            productSDao.insertProductSTrans(c_productLS,name,c_sessionS,amount,buyPrice,salePrice,descr,statePhoto,deficit,size,brand)
            sessionSDao.updateSessionSTransMore(c_sessionS)
        }
        if(sendAll){
            sessionLSDao.updateSessionLSTransLess(fk_c_sessionLS)
            productLSDao.deleteProductLSTrans(c_productLS)
        }else{
            productLSDao.updateProductLSTrans(amount,c_productLS)
        }
    }

    suspend fun transferProductS_LS(c_productS: String,name: String,fk_c_sessionS: String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,statePhoto: Int,c_sessionLS: String,deficit: Int,exists: Boolean,sendAll: Boolean,size: String,brand: String){
        if(exists){
            productLSDao.updateProductLSAmountTrannferPlus(amount,c_productS)
        }else{
            productLSDao.insertProductLSTrans(c_productS,name,c_sessionLS,amount,buyPrice,salePrice,descr,statePhoto,deficit,size,brand)
            sessionLSDao.updateSessionLSTransMore(c_sessionLS)
        }
        if(sendAll){
            sessionSDao.updateSessionSTransLess(fk_c_sessionS)
            productSDao.deleteProductSTrans(c_productS)
        }else{
            productSDao.updateProductSTransLess(amount,c_productS)
        }
    }

    suspend fun updateAccount(password:String,user:String){
        accountDao.updateAccount(password,user)
    }

    suspend fun insertAccount(user: String, password: String){
        accountDao.insertDefaultAccount(user, password)
    }

    suspend fun updateDrawerLS(c_drawerLSOld: String,c_drawerLSNew:String,fk_c_shelfLS: String,amount: Int){
        drawerLSDao.insertDrawerLSUp(c_drawerLSNew,fk_c_shelfLS,amount)
        sessionLSDao.updateSessionLSUp(c_drawerLSNew,c_drawerLSOld)
        drawerLSDao.deleteDrawerLSUp(c_drawerLSOld)
    }

    suspend fun updateDrawerS(c_drawerSOld: String,c_drawerSNew: String,fk_c_shelfS: String,amount: Int){
        drawerSDao.insertDrawerSUp(c_drawerSNew,fk_c_shelfS,amount)
        sessionSDao.updateSessionSUp(c_drawerSNew,c_drawerSOld)
        drawerSDao.deleteDrawerSUp(c_drawerSOld)
    }

    suspend fun updateProduct(c_productS: String,name: String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,deficit: Int,statePhoto: Int,size: String,brand: String){
        productSDao.updateProductS(name,amount,buyPrice,salePrice,descr,statePhoto,deficit,size,brand,c_productS)
    }

    suspend fun updateProductLS(c_productLS: String,name: String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,deficit: Int,size: String,brand: String, statePhoto: Int){
        productLSDao.updateProductLS(c_productLS,name,amount,buyPrice,salePrice,descr,statePhoto,deficit,size,brand)
    }

    suspend fun updateSessionLS(c_sessionLSOld: String,c_sessionLSNew: String,fk_c_drawerLS: String,amount: Int){
        sessionLSDao.insertSessionLSAmount(c_sessionLSNew,fk_c_drawerLS,amount)
        productLSDao.updateProductLSforain(c_sessionLSNew,c_sessionLSOld)
        sessionLSDao.deleteSessionLS(c_sessionLSOld)




    }

    suspend fun updateSessionS(c_sessionSOld: String,c_sessionSNew: String,fk_c_drawerS: String,amount: Int){
        sessionSDao.insertSessionSAmount(c_sessionSNew,fk_c_drawerS,amount)
        sessionSDao.updateProductSforain(c_sessionSNew,c_sessionSOld)
        sessionSDao.deleteSessionS(c_sessionSOld)
    }

    suspend fun updateShelfLS(c_shelfLSOld: String,c_shelfLSNew: String,amount: Int){
        shelfSLSDao.insertShelfLS(c_shelfLSNew)
        drawerLSDao.updateDrawerLSForain(c_shelfLSNew,c_shelfLSOld)
        shelfSLSDao.deleteShelfLSLower(c_shelfLSOld)

    }

    suspend fun updateShelfS(c_shelfSOld: String,c_shelfSNew: String,amount: Int){
        shelfSSDao.insertShelfS(c_shelfSNew)
        drawerSDao.updateDrawerSforain(c_shelfSNew,c_shelfSOld)
        shelfSSDao.deleteShelfS(c_shelfSOld)
    }

    suspend fun isDuplicatedS(c_productS: String): Boolean{
        return productSDao.selectDuplicatedProducts(c_productS).isNotEmpty()
    }

    suspend fun isDuplicatedLS(c_productLS: String): Boolean{
        return productLSDao.selectDuplicatedProducts(c_productLS).isNotEmpty()
    }

}