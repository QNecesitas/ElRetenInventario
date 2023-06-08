package com.qnecesitas.elreteninventario

import android.content.DialogInterface
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.qnecesitas.elreteninventario.adapters.AdapterRShelves
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelShelfS
import com.qnecesitas.elreteninventario.database.Repository
import com.qnecesitas.elreteninventario.databinding.FragmentShelvesBinding
import com.qnecesitas.elreteninventario.databinding.LiAddShelfBinding
import com.qnecesitas.elreteninventario.network.RetrofitShelvesImplS
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment_Shelves : Fragment() {

    private var openShelfS: OpenShelfS? = null

    //Recycler
    private lateinit var binding: FragmentShelvesBinding
    private lateinit var al_shelves: MutableList<ModelShelfS>
    private lateinit var adapterRShelves: AdapterRShelves

    //Internet
    private lateinit var repository: Repository

    //Add Shelf
    private lateinit var li_binding: LiAddShelfBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShelvesBinding.inflate(inflater)


        //Options
        binding.fsAdd.setOnClickListener { click_add() }

        //Refresh
        binding.refresh.setOnRefreshListener { loadRecyclerInfo() }

        //Recycler
        al_shelves = ArrayList()
        adapterRShelves = AdapterRShelves(al_shelves, binding.root.context)
        binding.fsRecycler.setHasFixedSize(true)
        binding.fsRecycler.layoutManager = LinearLayoutManager(binding.root.context)
        binding.fsRecycler.adapter = adapterRShelves

        //Retrofit
        repository = Repository(requireActivity().application)

        loadRecyclerInfo()
        return binding.root
    }


    //Recycler information
    private fun loadRecyclerInfo() {
        al_shelves = repository.fetchShelvesS()
        binding.fsRecycler.visibility = View.VISIBLE
        updateRecyclerAdapter()

    }

    private fun updateRecyclerAdapter() {
        if(al_shelves.isNotEmpty()){
            al_shelves.sortBy { it.c_shelfS }
        }

        if (al_shelves.isEmpty()) {
            binding.fsNotInfo.visibility = View.VISIBLE
            binding.fsRecycler.visibility = View.GONE
        } else {
            binding.fsNotInfo.visibility = View.GONE
            binding.fsRecycler.visibility = View.VISIBLE
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
                openShelfS?.onShelfSClicked(c_shelfS)
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
            if (tietContent.trim().isNotEmpty()){
                if (!isDuplicated(tietContent)) {
                    if (!tietContent.contains("_")) {
                        addNewShelfInternet(tietContent)
                        alertDialog.dismiss()
                    }else li_binding.til.error = getString(R.string.No_permitido_simbol)
                }else li_binding.til.error = getString(R.string.Ya_existe_el_estante)
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
        repository.addShelfS( shelfCode)

        val model = ModelShelfS(shelfCode, 0)
        al_shelves.add(model)
        updateRecyclerAdapter()
        FancyToast.makeText(
                requireContext(),
                getString(R.string.Operacion_realizada_con_exito),
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
        ).show()
    }

    private fun isDuplicated(name: String): Boolean{
        for(shelf in al_shelves){
            if(shelf.c_shelfS == name){
                return true
            }
        }
        return false
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
            if (tiedContent.trim().isNotEmpty()){
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
        repository.updateShelfS(shelfCodeOld, shelfCodeNew, al_shelves[position].amount)

        al_shelves[position].c_shelfS = shelfCodeNew
        updateRecyclerAdapter()
        FancyToast.makeText(
                requireContext(),
                getString(R.string.Operacion_realizada_con_exito),
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
        ).show()

    }


    //Delete shelf
    private fun click_delete(position: Int) {
        val amount = al_shelves[position].amount
        if(amount == 0) {
            showAlertDialogDeleteShelf(position)
        }else{
            showAlertDialogNotEmpty(amount)
        }
    }

    private fun showAlertDialogNotEmpty(amount: Int) {
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setCancelable(true)
            .setTitle(getString(R.string.elemento_no_vaciado))
            .setMessage(getString(R.string.debe_eliminar_todo,amount))
            .setPositiveButton(R.string.Aceptar) { dialog, _ ->
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
        ) { dialog, _ ->
                dialog.dismiss()
                deleteShelfInternet(al_shelves[position].c_shelfS, position)
        }
        builder.setNegativeButton(R.string.No,
            DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })

        //create the alert dialog and show it
        builder.create().show()
    }

    private fun deleteShelfInternet(shelfCode: String, position: Int) {
        repository.deleteShelfS(shelfCode)
        al_shelves.removeAt(position)
        updateRecyclerAdapter()
        FancyToast.makeText(
                requireContext(),
                getString(R.string.Operacion_realizada_con_exito),
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
        ).show()
    }

    fun setOpenShelfSListener(openShelfS: OpenShelfS) {
        this.openShelfS = openShelfS
    }

    interface OpenShelfS {
        fun onShelfSClicked(c_shelfS: String)
    }

}