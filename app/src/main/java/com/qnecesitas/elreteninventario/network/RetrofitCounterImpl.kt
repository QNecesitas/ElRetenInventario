package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelEditProduct
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class RetrofitCounterImpl : IRetrofitProductsCounter {

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(Constants.PHP_FILES)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productApi: IRetrofitProductsCounter = retrofit.create(IRetrofitProductsCounter::class.java)


    override fun fetchProducts(token: String): Call<ArrayList<ModelEditProduct>> {
        return productApi.fetchProducts(token)
    }
}