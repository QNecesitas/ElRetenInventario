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

    override fun updateDrawer(token: String, c_drawerSOld: String, c_drawerSNew: String): Call<String> {
        return productApi.updateDrawer(token, c_drawerSOld, c_drawerSNew)
    }

    override fun deleteDrawer(token: String, c_drawerS : String): Call<String> {
        return productApi.deleteDrawer(token, c_drawerS)
    }

    override fun addDrawer(token: String, c_drawerS: String,fk_c_shelfS: String): Call<String> {
        return productApi.addDrawer(token, c_drawerS,fk_c_shelfS)
    }

    override fun fetchDrawers(token: String, fk_c_shelfS: String): Call<ArrayList<ModelDrawer>> {
        return productApi.fetchDrawers(token,fk_c_shelfS)
    }


}