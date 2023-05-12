package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelEditProduct
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

    override fun fetchProducts(
        token: String,
        fk_c_sessionLS: String
    ): Call<ArrayList<ModelEditProduct>> {
        return productApi.fetchProducts(token, fk_c_sessionLS)
    }

    override fun fetchProductsSAll(token: String): Call<ArrayList<ModelEditProduct>> {
        return productApi.fetchProductsSAll(token)
    }


}