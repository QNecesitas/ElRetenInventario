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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.qnecesitas.elreteninventario.adapters.AdapterRShelves
import com.qnecesitas.elreteninventario.adapters.AdapterRShelvesLS
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelShelfLS
import com.qnecesitas.elreteninventario.data.ModelShelfS
import com.qnecesitas.elreteninventario.database.Repository
import com.qnecesitas.elreteninventario.databinding.FragmentShelvesLsBinding
import com.qnecesitas.elreteninventario.databinding.LiAddShelfBinding
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragment_ShelvesLS : Fragment() {

    private var openShelfS: OpenShelfLS? = null

    //Recycler
    private lateinit var binding: FragmentShelvesLsBinding
    private lateinit var al_shelves: MutableList<ModelShelfLS>
    private lateinit var adapterRShelves: AdapterRShelvesLS

    //Internet
    private lateinit var repository: Repository

    //Add Shelf
    private lateinit var li_binding: LiAddShelfBinding


    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShelvesLsBinding.inflate(inflater)


        //Options
        binding.fsAdd.setOnClickListener { click_add() }


        //Recycler
        al_shelves = ArrayList()
        adapterRShelves = AdapterRShelvesLS(al_shelves , binding.root.context)
        binding.fsRecycler.setHasFixedSize(true)
        binding.fsRecycler.layoutManager = LinearLayoutManager(binding.root.context)
        binding.fsRecycler.adapter = adapterRShelves

        //Retrofit
        repository = Repository(requireActivity().application as ElRetenApplication)

        //Internet
        loadRecyclerInfo()
        return binding.root
    }


    private fun loadRecyclerInfo() {
        lifecycleScope.launch {

            al_shelves = repository.fetchShelvesLS()
            updateRecyclerAdapter()
        }
    }


    private fun updateRecyclerAdapter() {
        if (al_shelves.isNotEmpty()) {
            al_shelves.sortBy { it.c_shelfLS }
            binding.fsNotInfo.visibility = View.GONE
        } else {
            binding.fsNotInfo.visibility = View.VISIBLE
        }

        if (al_shelves.isEmpty()) {
            binding.fsRecycler.visibility = View.GONE
        } else {
            binding.fsRecycler.visibility = View.VISIBLE
        }
        adapterRShelves = AdapterRShelvesLS(al_shelves , binding.root.context)
        adapterRShelves.setEditListener(object : AdapterRShelvesLS.RecyclerClickListener {
            override fun onClick(position: Int) {
                click_edit(position)
            }
        })
        adapterRShelves.setDeleteListener(object : AdapterRShelvesLS.RecyclerClickListener {
            override fun onClick(position: Int) {
                click_delete(position)
            }
        })
        adapterRShelves.setRecyclerTouchListener(object : AdapterRShelvesLS.RecyclerClickListener {
            override fun onClick(position: Int) {
                val c_shelfS = al_shelves.get(position).c_shelfLS
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
            if (tietContent.trim().isNotEmpty()) {
                if (!isDuplicated(tietContent)) {
                    if (!tietContent.contains("_")) {
                        addNewShelfInternet(tietContent)
                        alertDialog.dismiss()
                    } else li_binding.til.error = getString(R.string.No_permitido_simbol)
                } else li_binding.til.error = getString(R.string.Ya_existe_el_estante)
            } else li_binding.til.error = getString(R.string.este_campo_no_debe_vacio)
        }
        li_binding.btnCancel.setOnClickListener { alertDialog.dismiss() }

        //Finalizado
        builder.setCancelable(true)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun addNewShelfInternet(shelfCode: String) {
        lifecycleScope.launch {

            repository.addShelfLS(shelfCode)
        }
        val model = ModelShelfLS(shelfCode , 0)
        al_shelves.add(model)
        updateRecyclerAdapter()
        FancyToast.makeText(
            requireContext() ,
            getString(R.string.Operacion_realizada_con_exito) ,
            FancyToast.LENGTH_LONG ,
            FancyToast.SUCCESS ,
            false
        ).show()
    }

    private fun isDuplicated(name: String): Boolean {
        for (shelf in al_shelves) {
            if (shelf.c_shelfLS == name) {
                return true
            }
        }
        return false
    }

    //Edit shelf
    private fun click_edit(position: Int) {
        li_editShelf(al_shelves[position].c_shelfLS , position)
    }

    private fun li_editShelf(codeShelfOld: String , position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_binding = LiAddShelfBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_binding.root)
        val alertDialog = builder.create()
        var tiedContent: String;

        li_binding.tiet.setText(codeShelfOld)
        li_binding.btnAccept.setOnClickListener {
            tiedContent = li_binding.tiet.text.toString()
            if (tiedContent.trim().isNotEmpty()) {
                editShelfInternet(codeShelfOld , tiedContent , position)
                alertDialog.dismiss()
            } else li_binding.til.error = getString(R.string.este_campo_no_debe_vacio)
        }
        li_binding.btnCancel.setOnClickListener { alertDialog.dismiss() }

        //Finalizado
        builder.setCancelable(true)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun editShelfInternet(shelfCodeOld: String , shelfCodeNew: String , position: Int) {
        lifecycleScope.launch {

            repository.updateShelfLS(shelfCodeOld , shelfCodeNew , al_shelves[position].amount)
        }
        al_shelves[position].c_shelfLS = shelfCodeNew
        updateRecyclerAdapter()
        FancyToast.makeText(
            requireContext() ,
            getString(R.string.Operacion_realizada_con_exito) ,
            FancyToast.LENGTH_LONG ,
            FancyToast.SUCCESS ,
            false
        ).show()
    }


    //Delete shelf
    private fun click_delete(position: Int) {
        val amount = al_shelves[position].amount
        if (amount == 0) {
            showAlertDialogDeleteShelf(position)
        } else {
            showAlertDialogNotEmpty(amount)
        }
    }

    private fun showAlertDialogNotEmpty(amount: Int) {
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setCancelable(true)
            .setTitle(getString(R.string.elemento_no_vaciado))
            .setMessage(getString(R.string.debe_eliminar_todo , amount))
            .setPositiveButton(R.string.Aceptar) { dialog , _ ->
                dialog.dismiss()
            }

        //create the alert dialog and show it
        builder.create().show()
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
        ) { dialog , _ ->
            dialog.dismiss()
            deleteShelfInternet(al_shelves[position].c_shelfLS , position)
        }
        builder.setNegativeButton(R.string.No ,
            DialogInterface.OnClickListener { dialog , _ -> dialog.dismiss() })

        //create the alert dialog and show it
        builder.create().show()
    }

    private fun deleteShelfInternet(shelfCode: String , position: Int) {
        lifecycleScope.launch {

            repository.deleteShelfLS(shelfCode)
        }
        al_shelves.removeAt(position)
        updateRecyclerAdapter()
        FancyToast.makeText(
            requireContext() ,
            getString(R.string.Operacion_realizada_con_exito) ,
            FancyToast.LENGTH_LONG ,
            FancyToast.SUCCESS ,
            false
        ).show()
    }

    fun setOpenShelfLSListener(openShelfS: OpenShelfLS) {
        this.openShelfS = openShelfS
    }

    interface OpenShelfLS {
        fun onShelfLSClicked(c_shelfLS: String)
    }

}