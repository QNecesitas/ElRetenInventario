package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelEditProduct
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class RetrofitCartImpl : IRetrofitProductsCart {

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(Constants.PHP_FILES)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productApi: IRetrofitProductsCart = retrofit.create(IRetrofitProductsCart::class.java)

    override fun addOrder(
        token: String,
        name: String,
        products: String,
        totalPrice: Double,
        totalInv: Double,
        discount: Double,
        type: String,
        totalTransf: Double
    ): Call<String> {
        return productApi.addOrder(token, name, products, totalPrice, totalInv,discount,type, totalTransf)
    }
}