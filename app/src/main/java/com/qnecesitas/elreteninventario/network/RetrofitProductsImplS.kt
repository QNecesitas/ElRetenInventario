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


}