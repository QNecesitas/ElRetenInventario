package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelShelf
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class RetrofitShelvesImplS : IRetrofitShelvesS {

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(Constants.PHP_FILES)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productApi: IRetrofitShelvesS = retrofit.create(IRetrofitShelvesS::class.java)

    override fun updateShelf(token: String, c_ShelfSOld: String, c_ShelfSNew: String): Call<String> {
        return productApi.updateShelf(token, c_ShelfSOld, c_ShelfSNew)
    }

    override fun deleteShelf(token: String, c_ShelfS: String): Call<String> {
        return productApi.deleteShelf(token, c_ShelfS)
    }

    override fun addShelf(token: String, c_ShelfS: String): Call<String> {
        return productApi.addShelf(token, c_ShelfS)
    }

    override fun fetchShelves(token: String): Call<ArrayList<ModelShelf>> {
        return productApi.fetchShelves(token)
    }


}