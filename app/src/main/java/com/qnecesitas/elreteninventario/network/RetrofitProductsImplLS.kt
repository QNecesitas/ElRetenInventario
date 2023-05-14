package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelEditProduct
import com.qnecesitas.elreteninventario.data.ModelProductPath
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class RetrofitProductsImplLS : IRetrofitProductsLS {

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(Constants.PHP_FILES)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productApi: IRetrofitProductsLS = retrofit.create(IRetrofitProductsLS::class.java)

    override fun addProductLS(token: String, c_productS: String, name: String, c_fk_sessionS: String, amount: Int, buyPrice: Double, salePrice: Double, descr: String, file: String): Call<String>{
        return productApi.addProductLS(token,c_productS,name,c_fk_sessionS,amount,buyPrice,salePrice,descr,file)
    }

    override fun updateProductLS(
        token: String,
        c_productSOld: String,
        file: String,
        c_productS: String,
        name: String,
        c_fk_sessionS: String,
        amount: Int,
        buyPrice: Double,
        salePrice: Double,
        descr: String
    ): Call<String> {
        return productApi.updateProductLS(
            token, c_productSOld, file, c_productS, name, c_fk_sessionS, amount, buyPrice, salePrice, descr
        )
    }

    override fun fetchProductsLS(
        token: String,
        fk_c_sessionLS: String
    ): Call<ArrayList<ModelEditProduct>> {
        return productApi.fetchProductsLS(token, fk_c_sessionLS)
    }

    override fun fetchProductsSAllLS(token: String): Call<ArrayList<ModelEditProduct>> {
        return productApi.fetchProductsSAllLS(token)
    }

    override fun deleteProductLS(
        token: String,
        c_productS: String,
        fk_c_sessionS: String
    ): Call<String> {
        return productApi.deleteProductLS(token, c_productS, fk_c_sessionS)
    }

    override fun transferProductsLS(
        token : String,
        c_productS: String,
        name: String,
        fk_c_sessionS: String,
        amount: Int,
        buyPrice: Double,
        salePrice: Double,
        descr: String,
        statePhoto : Int,
        c_sessionLS: String
    ): Call<String> {
        return productApi.transferProductsLS(token, c_productS, name, fk_c_sessionS, amount, buyPrice, salePrice, descr, statePhoto, c_sessionLS)
    }

    override fun alterAmountSLS(token: String, c_productS: String, amount: Int): Call<String> {
        return  productApi.alterAmountSLS(token, c_productS, amount)
    }

    override fun fetchProductSPathLS(token: String, c_productS: String): Call<ArrayList<ModelProductPath>> {
        return  productApi.fetchProductSPathLS(token, c_productS)
    }


}