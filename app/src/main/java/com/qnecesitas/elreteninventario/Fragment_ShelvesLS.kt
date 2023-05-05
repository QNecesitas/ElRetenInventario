package com.qnecesitas.elreteninventario

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.qnecesitas.elreteninventario.adapters.AdapterRShelves
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.auxiliary.NetworkTools
import com.qnecesitas.elreteninventario.data.ModelShelf
import com.qnecesitas.elreteninventario.databinding.FragmentShelvesLsBinding
import com.qnecesitas.elreteninventario.databinding.LiAddShelfBinding
import com.qnecesitas.elreteninventario.network.RetrofitShelvesImplLS
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragment_ShelvesLS : Fragment() {

    private var openShelfS: OpenShelfLS? = null

    //Recycler
    private lateinit var binding: FragmentShelvesLsBinding
    private lateinit var al_shelves: ArrayList<ModelShelf>
    private lateinit var adapterRShelves: AdapterRShelves

    //Internet
    private lateinit var retrofitShelvesImpl: RetrofitShelvesImplLS

    //Add Shelf
    private lateinit var li_binding: LiAddShelfBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShelvesLsBinding.inflate(inflater)


        //Options
        binding.fsAdd.setOnClickListener { click_add() }

        //Refresh
        binding.refresh.setOnRefreshListener( object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                binding.refresh.isRefreshing = true
                loadRecyclerInfo()
                binding.refresh.isRefreshing = false
            }
        } )

        //Recycler
        al_shelves = ArrayList()
        adapterRShelves = AdapterRShelves(al_shelves, binding.root.context)
        binding.fsRecycler.setHasFixedSize(true)
        binding.fsRecycler.layoutManager = LinearLayoutManager(binding.root.context)
        binding.fsRecycler.adapter = adapterRShelves

        //Retrofit
        retrofitShelvesImpl = RetrofitShelvesImplLS()

        //Internet
        binding.fsRetryConnection.setOnClickListener {
            binding.refresh.isRefreshing = true
            loadRecyclerInfo()
        }
        loadRecyclerInfo()
        return binding.root
    }


    //Recycler information
    private fun loadRecyclerInfo() {
        binding.refresh.isRefreshing = true
        if (NetworkTools.isOnline(binding.root.context, false)) {

            val call = retrofitShelvesImpl.fetchShelves(Constants.PHP_TOKEN)
            call.enqueue(object : Callback<ArrayList<ModelShelf>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelShelf>>,
                    response: Response<java.util.ArrayList<ModelShelf>>
                ) {
                    if (response.isSuccessful) {
                        binding.fsNotConnection.visibility = View.GONE
                        binding.fsRecycler.visibility = View.VISIBLE
                        binding.fsNotInfo.visibility = View.GONE
                        al_shelves = response.body()!!
                        updateRecyclerAdapter()
                    } else {
                        binding.fsNotConnection.visibility = View.VISIBLE
                        binding.fsRecycler.visibility = View.GONE
                        binding.fsNotInfo.visibility = View.GONE
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelShelf>>,
                    t: Throwable
                ) {
                    binding.fsNotConnection.visibility = View.VISIBLE
                    binding.fsRecycler.visibility = View.GONE
                    binding.fsNotInfo.visibility = View.GONE
                }
            })


        } else {
            binding.fsNotConnection.visibility = View.VISIBLE
            binding.fsRecycler.visibility = View.GONE
            binding.fsNotInfo.visibility = View.GONE
        }
        binding.refresh.isRefreshing = false
    }

    private fun updateRecyclerAdapter() {
        if (al_shelves.isEmpty()) {
            binding.fsNotInfo.visibility = View.VISIBLE
            binding.fsRecycler.visibility = View.GONE
            binding.fsNotConnection.visibility = View.GONE
        } else {
            binding.fsNotInfo.visibility = View.GONE
            binding.fsRecycler.visibility = View.VISIBLE
            binding.fsNotConnection.visibility = View.GONE
        }
        adapterRShelves = AdapterRShelves(al_shelves, binding.root.context)
        adapterRShelves.setEditListener(object : AdapterRShelves.RecyclerClickListener {
            override fun onClick(position: Int) {
                click_edit(position)
            }
        })
        adapterRShelves.setDeleteListener(object : AdapterRShelves.RecyclerClickListener {
            override fun onClick(position: Int) {
                click_delete(position)
            }
        })
        adapterRShelves.setRecyclerTouchListener(object : AdapterRShelves.RecyclerClickListener {
            override fun onClick(position: Int) {
                val c_shelfS = al_shelves.get(position).c_shelfS
                openShelfS?.onShelfLSClicked(c_shelfS)
            }

        })
        binding.fsRecycler.adapter = adapterRShelves
    }


    //Add shelf
    private fun click_add() {
        li_newShelf()
    }

    private fun li_newShelf() {
        val inflater = LayoutInflater.from(binding.root.context)
        li_binding = LiAddShelfBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_binding.root)
        val alertDialog = builder.create()
        var tietContent: String;

        li_binding.btnAccept.setOnClickListener {
            tietContent = li_binding.tiet.text.toString()
            if (tietContent.isNotEmpty()){
                binding.refresh.isRefreshing = true
                addNewShelfInternet(tietContent)
                alertDialog.dismiss()
            }
            else li_binding.til.error = getString(R.string.este_campo_no_debe_vacio)
        }
        li_binding.btnCancel.setOnClickListener { alertDialog.dismiss() }

        //Finalizado
        builder.setCancelable(true)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun addNewShelfInternet(shelfCode: String) {
        if (NetworkTools.isOnline(binding.root.context, true)) {
            val call = retrofitShelvesImpl.addShelf(Constants.PHP_TOKEN, shelfCode)
            call.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        val model = ModelShelf(shelfCode, 0)
                        al_shelves.add(model)
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
                    call: Call<String>,
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
        binding.refresh.isRefreshing = false
    }


    //Edit shelf
    private fun click_edit(position: Int) {
        li_editShelf(al_shelves[position].c_shelfS, position)
    }

    private fun li_editShelf(codeShelfOld: String, position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_binding = LiAddShelfBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_binding.root)
        val alertDialog = builder.create()
        var tiedContent: String;

        li_binding.tiet.setText(codeShelfOld)
        li_binding.btnAccept.setOnClickListener {
            tiedContent = li_binding.tiet.text.toString()
            if (tiedContent.isNotEmpty()){
                binding.refresh.isRefreshing = true
                editShelfInternet(codeShelfOld, tiedContent, position)
                alertDialog.dismiss()
            }
            else li_binding.til.error = getString(R.string.este_campo_no_debe_vacio)
        }
        li_binding.btnCancel.setOnClickListener { alertDialog.dismiss() }

        //Finalizado
        builder.setCancelable(true)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun editShelfInternet(shelfCodeOld: String, shelfCodeNew: String, position: Int) {
        if (NetworkTools.isOnline(binding.root.context, true)) {
            val call =
                retrofitShelvesImpl.updateShelf(Constants.PHP_TOKEN, shelfCodeOld, shelfCodeNew, al_shelves[position].amount)
            call.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        al_shelves[position].c_shelfS = shelfCodeNew
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
                    call: Call<String>,
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
        binding.refresh.isRefreshing = false
    }


    //Delete shelf
    private fun click_delete(position: Int) {
        showAlertDialogDeleteShelf(position)
    }

    private fun showAlertDialogDeleteShelf(position: Int) {
        //init alert dialog
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setCancelable(true)
        builder.setTitle(R.string.Eliminar_elemento)
        builder.setMessage(R.string.Desea_eliminar_el_estante)
        //set listeners for dialog buttons
        builder.setPositiveButton(
            R.string.Si
        ) { dialog, _ ->
            if (NetworkTools.isOnline(requireContext(), true)) {
                dialog.dismiss()
                binding.refresh.isRefreshing = true
                deleteShelfInternet(al_shelves[position].c_shelfS, position)
            }
        }
        builder.setNegativeButton(R.string.No,
            DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })

        //create the alert dialog and show it
        builder.create().show()
    }

    private fun deleteShelfInternet(shelfCode: String, position: Int) {
        if (NetworkTools.isOnline(binding.root.context, true)) {
            val call = retrofitShelvesImpl.deleteShelf(Constants.PHP_TOKEN, shelfCode)
            call.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        al_shelves.removeAt(position)
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
                    call: Call<String>,
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
        binding.refresh.isRefreshing = false
    }

    fun setOpenShelfLSListener(openShelfS: OpenShelfLS) {
        this.openShelfS = openShelfS
    }

    interface OpenShelfLS {
        fun onShelfLSClicked(c_shelfLS: String)
    }

}