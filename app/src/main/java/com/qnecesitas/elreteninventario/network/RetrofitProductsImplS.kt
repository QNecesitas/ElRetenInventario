package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelEditProduct
import com.qnecesitas.elreteninventario.data.ModelSession
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

    override fun addProduct(token: String, c_productS: String, name: String, c_fk_sessionS: String, amount: Int, buyPrice: Double, salePrice: Double, descr: String, file: String): Call<String>{
        return productApi.addProduct(token,c_productS,name,c_fk_sessionS,amount,buyPrice,salePrice,descr,file)
    }

    override fun updateProduct(
        token: String,
        c_productS: String,
        name: String,
        c_fk_sessionS: String,
        amount: Int,
        buyPrice: Double,
        salePrice: Double,
        descr: String,
        file: String
    ): Call<String> {
        return productApi.updateProduct(token, c_productS, name, c_fk_sessionS, amount, buyPrice, salePrice, descr, file)
    }

    override fun fetchProducts(
        token: String,
        fk_c_sessionS: String
    ): Call<ArrayList<ModelEditProduct>> {
        return productApi.fetchProducts(token, fk_c_sessionS)
    }

    override fun fetchProductsSAll(
        token: String
    ): Call<ArrayList<ModelEditProduct>> {
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
        filter: Int,
        button: String
    ): Call<ArrayList<ModelEditProduct>> {
        return productApi.fetchProductsDeficit(token,filter,button)
    }

}