package com.qnecesitas.elreteninventario

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.qnecesitas.elreteninventario.adapters.AdapterRDrawers
import com.qnecesitas.elreteninventario.adapters.AdapterRShelves
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.auxiliary.NetworkTools
import com.qnecesitas.elreteninventario.data.ModelDrawer
import com.qnecesitas.elreteninventario.data.ModelShelf
import com.qnecesitas.elreteninventario.databinding.FragmentDrawersBinding
import com.qnecesitas.elreteninventario.databinding.LiAddShelfBinding
import com.qnecesitas.elreteninventario.network.RetrofitDrawersImplS
import com.qnecesitas.elreteninventario.network.RetrofitShelvesImplS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment_Drawers(var c_shelfS : String): Fragment() {

    //Recycler
    private lateinit var binding: FragmentDrawersBinding
    private lateinit var al_drawers: ArrayList<ModelDrawer>
    private lateinit var adapterRDrawers: AdapterRDrawers

    //Internet
    private lateinit var retrofitDrawersImpl: RetrofitDrawersImplS

    //Add Shelf
    private lateinit var li_binding: LiAddShelfBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrawersBinding.inflate(inflater)


        //Add Button
        binding.fdAdd.setOnClickListener { click_add() }

        //Recycler
        al_drawers = ArrayList()
        adapterRDrawers = AdapterRDrawers(al_drawers, binding.root.context)
        binding.fdRecycler.setHasFixedSize(true)
        binding.fdRecycler.layoutManager = LinearLayoutManager(binding.root.context)
        binding.fdRecycler.adapter = adapterRDrawers

        //Retrofit
        retrofitDrawersImpl = RetrofitDrawersImplS()

        //Internet
        binding.fdRetryConnection.setOnClickListener { loadRecyclerInfo() }
        loadRecyclerInfo()
        return binding.root
    }

    //Recycler information
    private fun loadRecyclerInfo() {
        if (NetworkTools.isOnline(binding.root.context, false)) {

            val call = retrofitDrawersImpl.fetchDrawers(Constants.PHP_TOKEN)
            call.enqueue(object : Callback<ArrayList<ModelDrawer>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelDrawer>>,
                    response: Response<java.util.ArrayList<ModelDrawer>>
                ) {
                    if (response.isSuccessful) {
                        binding.fdNotConnection.visibility = View.GONE
                        binding.fdRecycler.visibility = View.VISIBLE
                        al_drawers = response.body()!!
                        updateRecyclerAdapter()
                    } else {
                        binding.fdNotConnection.visibility = View.VISIBLE
                        binding.fdRecycler.visibility = View.GONE
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelDrawer>>,
                    t: Throwable
                ) {
                    binding.fdNotConnection.visibility = View.VISIBLE
                    binding.fdRecycler.visibility = View.GONE
                }
            })


        } else {
            binding.fdNotConnection.visibility = View.VISIBLE
            binding.fdRecycler.visibility = View.GONE
        }
    }

    private fun updateRecyclerAdapter() {
        if (al_drawers.isEmpty()) {
            binding.fdNotInfo.visibility = View.VISIBLE
            binding.fdRecycler.visibility = View.GONE
        } else {
            binding.fdNotInfo.visibility = View.GONE
            binding.fdRecycler.visibility = View.VISIBLE
        }
        adapterRDrawers = AdapterRDrawers(al_drawers, binding.root.context)
        binding.fdRecycler.adapter = adapterRDrawers
    }

    //Add shelf
    private fun click_add() {
        li_newDrawer()
    }

    private fun li_newDrawer() {
        val inflater = LayoutInflater.from(binding.root.context)
        li_binding = LiAddShelfBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_binding.root)
        val alertDialog = builder.create()


        //Finalizado
        builder.setCancelable(true)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()


    }

}
