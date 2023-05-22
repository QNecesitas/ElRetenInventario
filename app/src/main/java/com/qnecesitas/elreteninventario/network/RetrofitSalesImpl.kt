package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelSale
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitSalesImpl: IRetrofitSales {

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(Constants.PHP_FILES)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productApi: IRetrofitSales = retrofit.create(IRetrofitSales::class.java)

    override fun fetchOrdersAll(token: String): Call<ArrayList<ModelSale>>{
        return productApi.fetchOrdersAll(token)
    }

    override fun fetchOrdersY(token: String, year: Int): Call<ArrayList<ModelSale>>{
        return productApi.fetchOrdersY(token, year)
    }

    override fun fetchOrdersM(token: String, year: Int, month: Int): Call<ArrayList<ModelSale>>{
        return productApi.fetchOrdersM(token,year , month)
    }

    override fun fetchOrdersD(token: String, year: Int, month: Int, day: Int): Call<ArrayList<ModelSale>>{
        return productApi.fetchOrdersD(token, year, month, day)
    }

    override fun deleteOrder(token: String, c_order: Int): Call<String> {
        return productApi.deleteOrder(token, c_order)
    }

}