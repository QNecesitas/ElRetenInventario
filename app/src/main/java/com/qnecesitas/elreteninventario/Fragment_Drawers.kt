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
import com.qnecesitas.elreteninventario.databinding.LiAddDrawerBinding
import com.qnecesitas.elreteninventario.databinding.LiAddShelfBinding
import com.qnecesitas.elreteninventario.network.RetrofitDrawersImplS
import com.qnecesitas.elreteninventario.network.RetrofitShelvesImplS
import com.shashank.sony.fancytoastlib.FancyToast
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
    private lateinit var li_binding: LiAddDrawerBinding


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

            val call = retrofitDrawersImpl.fetchDrawers(Constants.PHP_TOKEN,c_shelfS)
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
        li_binding = LiAddDrawerBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_binding.root)
        val alertDialog = builder.create()
        var tietContent: String;

        li_binding.btnAccept.setOnClickListener {
            tietContent = li_binding.tiet.text.toString()
            if (tietContent.isNotEmpty()) addNewDrawerInternet(tietContent)
            else li_binding.til.error = getString(R.string.este_campo_no_debe_vacio)
            alertDialog.dismiss()
        }
        li_binding.btnCancel.setOnClickListener { alertDialog.dismiss() }

        //Finalizado
        builder.setCancelable(true)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()


    }

    private fun addNewDrawerInternet(drawerCode: String) {
        if (NetworkTools.isOnline(binding.root.context, true)) {
            val call = retrofitDrawersImpl.addDrawer(Constants.PHP_TOKEN, drawerCode,c_shelfS)
            call.enqueue(object : Callback<Boolean> {
                override fun onResponse(
                    call: Call<Boolean>,
                    response: Response<Boolean>
                ) {
                    if (response.isSuccessful) {
                        val model = ModelDrawer(drawerCode, c_shelfS)
                        al_drawers.add(model)
                        updateRecyclerAdapter()
                        FancyToast.makeText(
                            requireContext(),
                            getString(R.string.Operacion_realizada_con_exito),
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false
                        ).show()
                    } else {
                        FancyToast.makeText(
                            requireContext(),
                            getString(R.string.Revise_su_conexion),
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<Boolean>,
                    t: Throwable
                ) {
                    FancyToast.makeText(
                        requireContext(),
                        getString(R.string.Revise_su_conexion),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false
                    ).show()
                }
            })
        }
    }

}
