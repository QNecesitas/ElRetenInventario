package com.qnecesitas.elreteninventario.database

import android.app.Application
import com.qnecesitas.elreteninventario.ElRetenApplication

class Repository(private val application: Application) {
    val drawerLSDao = (application as ElRetenApplication).database.drawerLSDao()
    val shelfSLSDao = (application as ElRetenApplication).database.shelfLSDao()
    val drawerSDao = (application as ElRetenApplication).database.drawerSDao()
    val shelfSSDao = (application as ElRetenApplication).database.shelfSDao()
    val ordersDao = (application as ElRetenApplication).database.ordersDao()
    val sessionSDao = (application as ElRetenApplication).database.sessionS()
    val sessionLSDao = (application as ElRetenApplication).database.sessionLS()
    val productSDao = (application as ElRetenApplication).database.productS()
    val productLSDao = (application as ElRetenApplication).database.productLS()

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
        productSDao.insertProducts(c_productS,name,fk_c_sessionS,amount,buyPrice,salePrice,descr,statePhoto,deficit,size,brand)
        sessionSDao.updateSessionSmore(fk_c_sessionS)
    }

    fun addProductLS(c_productS: String, name: String, fk_c_sessionS: String, amount: Int, buyPrice: Double, salePrice: Double, descr: String, file: String, deficit: Int, statePhoto: Int, size: String, brand: String){
        productLSDao.insertProductLS(c_productS,name,fk_c_sessionS,amount,buyPrice,salePrice,descr,statePhoto,deficit,size,brand)
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

    fun fetchAccounts(){
        //TODO SELECT Account
    }

    fun fetchDrawersLS(fk_c_shelfLS: String){
        //TODO SELECT DrawerLS
    }

    fun fetchDrawersS(fk_c_shelfS: String){
        //TODO SELECT DrawerS
    }

    fun fetchOrdersAll(){
        //TODO SELECT Orders
    }

    fun fetchOrdersD(year:Int,month: Int,day: Int){
        //TODO SELECT Orders
    }

    fun fetchOrdersM(year: Int,month: Int){
        //TODO SELECT Orders
    }

    fun fetchOrdersY(year: Int){
        //TODO SELECT Orders
    }

    fun fetchProductLSPath(c_productLS: String){
        //TODO SELECT ProductLS
    }

    fun fetchProductSPath(c_productS: String){
        //TODO SELECT ProductS
    }

    fun fetchProductsCounter(){
        //TODO SELECT ProductC
    }

    fun fetchProductsDeficit(button:String){
        //TODO SELECT ProductS
        //TODO SELECT ProductLS
    }

    fun fetchProductsLS(fk_c_sessionLS: String){
        //TODO SELECT ProductLS
    }

    fun fetchProductsLSAll(){
        //TODO SELECT ProductLS
    }

    fun fetchProductsS(fk_c_sessionS:String){
        //TODO SELECT ProductS
    }

    fun fetchProductsSAll(){
        //TODO SELECT ProductS
        //TODO SELECT ProductLS
    }

    fun fetchSessionsLS(fk_c_drawerLS: String){
        //TODO SELECT SessionLS
    }

    fun fetchSessionsS(fk_c_drawerS: String){
        //TODO SELECT SessionS
    }

    fun fetchShelvesLS(){
        //TODO SELECT ShelfLS
    }

    fun fetchShelvesS(){
        //TODO SELECT ShelfS
    }

    fun transferProductLS_S(c_productLS: String,name: String,fk_c_sessionLS: String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,statePhoto: Int,c_sessionS: String,deficit: Int,exists:Int,sendAll: Int,size: String,brand: String){
        if(exists == 1){
            //TODO UPDATE ProductS
        }else{
            //TODO INSERT ProductS
            //TODO UPDATE SessionS
        }
        if(sendAll == 1){
            //TODO UPDATE SessionLS
            //TODO DELETE ProductLS
        }else{
            //TODO UPDATE ProductLS
        }
    }

    fun transferProductS_LS(c_productS: String,name: String,fk_c_sessionS: String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,statePhoto: Int,c_sessionLS: String,deficit: Int,exists: Int,sendAll: Int,size: String,brand: String){
        if(exists == 1){
            //TODO UPDATE ProductLS
        }else{
            //TODO INSERT ProductLS
            //TODO UPDATE SessionLS
        }
        if(sendAll == 1){
            //TODO UPDATE SessionS
            //TODO DELETE ProductS
        }else{
            //TODO UPDATE ProductS
        }
    }

    fun updateAccount(password:String,user:String){
        //TODO UPDATE Account
    }

    fun updateDrawerLS(c_drawerLSOld: String,c_drawerLSNew:String,fk_c_shelfLS: String,amount: Int){
        //TODO INSERT DrawerLS
        //TODO UPDATE SessionLS
        //TODO DELETE DrawerLS
    }

    fun updateDrawerS(c_drawerSOld: String,c_drawerSNew: String,fk_c_shelfS: String,amount: Int){
        //TODO INSERT DrawerS
        //TODO UPDATE SessionS
        //TODO DELETE DrawerS
    }

    fun updateProduct(file: String,c_productS: String,name: String,fk_c_sessionS: String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,deficit: Int,statePhoto: Int,namePhoto:String,path:String,size: String,brand: String){
        productSDao.updateProductS(name,amount,buyPrice,salePrice,descr,statePhoto,deficit,size,brand,c_productS)
    }

    fun updateProductLS(c_productLSOld: String,file: String,c_productLS: String,name: String,fk_c_sessionLS: String,amount: Int,buyPrice: Double,salePrice: Double,descr: String,deficit: Int,statePhoto: Int,namePhoto: String,namePhotoNew: String,path: String,pathNew: String,size: String,brand: String){
        productLSDao.updateProductLS(c_productLS,name,amount,buyPrice,salePrice,descr,statePhoto,deficit,size,brand,c_productLSOld)

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