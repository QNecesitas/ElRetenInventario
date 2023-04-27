package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelDrawer
import com.qnecesitas.elreteninventario.data.ModelShelf
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class RetrofitDrawersImplS : IRetrofitDrawersS {

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(Constants.PHP_FILES)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productApi: IRetrofitDrawersS = retrofit.create(IRetrofitDrawersS::class.java)

    override fun updateDrawer(token: String, codeDrawer : String): Call<Boolean> {
        return productApi.updateDrawer(token, codeDrawer)
    }

    override fun deleteDrawer(token: String, codeDrawer : String): Call<Boolean> {
        return productApi.deleteDrawer(token, codeDrawer)
    }

    override fun addDrawer(token: String, codeShelf: String): Call<Boolean> {
        return productApi.addDrawer(token, codeShelf)
    }

    override fun fetchDrawers(token: String): Call<ArrayList<ModelDrawer>> {
        return productApi.fetchDrawers(token)
    }


}