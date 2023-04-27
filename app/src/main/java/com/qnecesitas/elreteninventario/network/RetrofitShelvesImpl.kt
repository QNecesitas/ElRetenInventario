package com.qnecesitas.elreteninventario.network

import android.provider.SyncStateContract
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelShelf
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class RetrofitShelvesImpl : IRetrofitShelves {

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(Constants.PHP_FILES)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productApi: IRetrofitShelves = retrofit.create(IRetrofitShelves::class.java)

    override fun updateShelf(token: String, codeShelf: String): Call<Boolean> {
        return productApi.updateShelf(token, codeShelf)
    }

    override fun deleteShelf(token: String, codeShelf: String): Call<Boolean> {
        return productApi.deleteShelf(token, codeShelf)
    }

    override fun addShelf(token: String, codeShelf: String): Call<Boolean> {
        return productApi.addShelf(token, codeShelf)
    }

    override fun fetchShelves(token: String, isCounter: Boolean): Call<ArrayList<ModelShelf>> {
        return productApi.fetchShelves(token, isCounter)
    }


}