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

    override fun updateShelf(token: String, c_shelfSOld: String, c_shelfSNew: String): Call<String> {
        return productApi.updateShelf(token, c_shelfSOld, c_shelfSNew)
    }

    override fun deleteShelf(token: String, c_shelfS: String): Call<String> {
        return productApi.deleteShelf(token, c_shelfS)
    }

    override fun addShelf(token: String, c_shelfS: String): Call<String> {
        return productApi.addShelf(token, c_shelfS)
    }

    override fun fetchShelves(token: String): Call<ArrayList<ModelShelf>> {
        return productApi.fetchShelves(token)
    }


}