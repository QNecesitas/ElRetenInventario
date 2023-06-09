package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelEditProductS
import com.qnecesitas.elreteninventario.data.ModelProductPath
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class RetrofitProductsImplS : IRetrofitProductsS {

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(Constants.PHP_FILES)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productApi: IRetrofitProductsS = retrofit.create(IRetrofitProductsS::class.java)

    override fun addProduct(token: String, c_productS: String, name: String, c_fk_sessionS: String, amount: Int, buyPrice: Double, salePrice: Double, descr: String, file: String, deficit: Int,size: String ,brand : String): Call<String>{
        return productApi.addProduct(token,c_productS,name,c_fk_sessionS,amount,buyPrice,salePrice,descr,file,deficit,size,brand)
    }

    override fun updateProduct(
        token: String,
        file: String,
        c_productS: String,
        name: String,
        c_fk_sessionS: String,
        amount: Int,
        buyPrice: Double,
        salePrice: Double,
        descr: String,
        deficit: Int,
        statePhoto: Int,
        size: String,
        brand: String
    ): Call<String> {
        return productApi.updateProduct(
            token, file, c_productS, name, c_fk_sessionS, amount, buyPrice, salePrice, descr,deficit,statePhoto,size,brand
        )
    }


    override fun fetchProducts(
        token: String,
        fk_c_sessionS: String
    ): Call<ArrayList<ModelEditProductS>> {
        return productApi.fetchProducts(token, fk_c_sessionS)
    }

    override fun fetchProductsSAll(
        token: String
    ): Call<ArrayList<ModelEditProductS>> {
        return productApi.fetchProductsSAll(token)
    }

    override fun deleteProduct(
        token: String,
        c_productS: String,
        fk_c_sessionS: String
    ): Call<String> {
        return productApi.deleteProduct(token, c_productS, fk_c_sessionS)
    }


    override fun fetchProductsDeficit(
        token: String,
        button: String
    ): Call<ArrayList<ModelEditProductS>> {
        return productApi.fetchProductsDeficit(token,button)
    }

    override fun transferProducts(
        token : String,
        c_productS: String,
        name: String,
        fk_c_sessionS: String,
        amount: Int,
        buyPrice: Double,
        salePrice: Double,
        descr: String,
        statePhoto : Int,
        c_sessionLS: String,
        deficit: Int,
        exists: Int,
        sendAll: Int,
        size: String,
        brand: String
    ): Call<String> {
        return productApi.transferProducts(token, c_productS, name, fk_c_sessionS, amount, buyPrice, salePrice, descr, statePhoto, c_sessionLS,deficit, exists, sendAll,size,brand)
    }

    override fun alterAmountS(token: String, c_productS: String, amount: Int): Call<String> {
        return  productApi.alterAmountS(token, c_productS, amount)
    }

    override fun fetchProductSPath(token: String, c_productS: String): Call<ArrayList<ModelProductPath>> {
        return  productApi.fetchProductSPath(token, c_productS)
    }


}