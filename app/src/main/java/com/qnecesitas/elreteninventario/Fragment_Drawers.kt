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
import com.qnecesitas.elreteninventario.adapters.AdapterRDrawers
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelDrawerS
import com.qnecesitas.elreteninventario.database.Repository
import com.qnecesitas.elreteninventario.databinding.FragmentDrawersBinding
import com.qnecesitas.elreteninventario.databinding.LiAddDrawerBinding
import com.qnecesitas.elreteninventario.network.RetrofitDrawersImplS
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment_Drawers(var c_shelfS : String): Fragment() {

    private var openDrawer: OpenDrawerS? = null
    //Recycler
    private lateinit var binding: FragmentDrawersBinding
    private lateinit var al_drawers: MutableList<ModelDrawerS>
    private lateinit var adapterRDrawers: AdapterRDrawers

    //Internet
    private lateinit var repository: Repository

    //Add Drawer
    private lateinit var li_binding: LiAddDrawerBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrawersBinding.inflate(inflater)


        //Add Button
        binding.fdAdd.setOnClickListener { click_add() }

        //Refresh
        binding.refresh.setOnRefreshListener( object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                loadRecyclerInfo()
            }
        } )

        //Recycler
        al_drawers = ArrayList()
        adapterRDrawers = AdapterRDrawers(al_drawers, binding.root.context)
        binding.fdRecycler.setHasFixedSize(true)
        binding.fdRecycler.layoutManager = LinearLayoutManager(binding.root.context)
        binding.fdRecycler.adapter = adapterRDrawers

        //Retrofit
        repository = Repository(requireActivity().application as ElRetenApplication)


        loadRecyclerInfo()
        return binding.root
    }

    //Recycler information
    private fun loadRecyclerInfo() {

        al_drawers = repository.fetchDrawersS(c_shelfS)

        binding.fdRecycler.visibility = View.VISIBLE
        binding.fdNotInfo.visibility = View.GONE
        updateRecyclerAdapter()

    }

    private fun updateRecyclerAdapter() {
        if(al_drawers.isNotEmpty()){
            al_drawers.sortBy { it.c_drawerS }
        }

        if (al_drawers.isEmpty()) {
            binding.fdNotInfo.visibility = View.VISIBLE
            binding.fdRecycler.visibility = View.GONE
        } else {
            binding.fdNotInfo.visibility = View.GONE
            binding.fdRecycler.visibility = View.VISIBLE
        }
        adapterRDrawers = AdapterRDrawers(al_drawers , binding.root.context)

        adapterRDrawers.setEditListener(object : AdapterRDrawers.RecyclerClickListener {
            override fun onClick(position: Int) {
                click_edit(position)
            }
        })
        adapterRDrawers.setDeleteListener(object: AdapterRDrawers.RecyclerClickListener{
            override fun onClick(position: Int) {
                click_delete(position)
            }
        })
        adapterRDrawers.setTouchListener(object: AdapterRDrawers.RecyclerClickListener{
            override fun onClick(position: Int) {
                val c_drawer = al_drawers.get(position).c_drawerS
                openDrawer?.onDrawerSClicked(c_drawer)
            }

        })
        binding.fdRecycler.adapter = adapterRDrawers
    }

    //Add Drawer
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
            if (tietContent.trim().isNotEmpty()){
                if(!isDuplicated(tietContent)) {
                    if(!tietContent.contains("_")) {
                        addNewDrawerInternet(tietContent)
                        alertDialog.dismiss()
                    }else li_binding.til.error = getString(R.string.No_permitido_simbol)
                }else li_binding.til.error = getString(R.string.Ya_existe_gaveta)
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

    private fun addNewDrawerInternet(drawerCode: String) {

        repository.addDrawerS(drawerCode , c_shelfS)

        val model = ModelDrawerS(drawerCode , c_shelfS)
        al_drawers.add(model)
        updateRecyclerAdapter()
        FancyToast.makeText(
            requireContext() ,
            getString(R.string.Operacion_realizada_con_exito) ,
            FancyToast.LENGTH_LONG ,
            FancyToast.SUCCESS ,
            false
        ).show()

    }

    private fun isDuplicated(name: String): Boolean{
        for(shelf in al_drawers){
            val positionSymbol = shelf.c_drawerS.lastIndexOf("_")
            val codePrepared = shelf.c_drawerS.substring(positionSymbol+1)
            if(codePrepared == name){
                return true
            }
        }
        return false
    }

    //Edit drawer
    private fun click_edit(position: Int) {
        li_editDrawer(al_drawers[position].c_drawerS, position)
    }

    private fun li_editDrawer(codeDrawerOld: String, position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_binding = LiAddDrawerBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_binding.root)
        val alertDialog = builder.create()
        var tiedContent: String;

        val guionPosition = codeDrawerOld.lastIndexOf("_")
        val newCode = codeDrawerOld.substring(guionPosition + 1)
        li_binding.tiet.setText(newCode)
        li_binding.btnAccept.setOnClickListener {
            tiedContent = li_binding.tiet.text.toString()
            if (tiedContent.trim().isNotEmpty()){
                editDrawerInternet(codeDrawerOld, tiedContent, position)
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

    private fun editDrawerInternet(drawerCodeOld: String , drawerCodeNew: String , position: Int) {

        repository.updateDrawerS(
            drawerCodeOld ,
            drawerCodeNew ,
            al_drawers[position].fk_c_shelfS ,
            al_drawers[position].amount
        )

        al_drawers[position].c_drawerS = drawerCodeNew
        updateRecyclerAdapter()
        FancyToast.makeText(
            requireContext() ,
            getString(R.string.Operacion_realizada_con_exito) ,
            FancyToast.LENGTH_LONG ,
            FancyToast.SUCCESS ,
            false
        ).show()

    }


    //Delete drawer
    private fun click_delete(position: Int) {
        val amount = al_drawers[position].amount
        if(amount == 0) {
            showAlertDialogDeleteDrawer(position)
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

    private fun showAlertDialogDeleteDrawer(position: Int) {
        //init alert dialog
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setCancelable(true)
        builder.setTitle(R.string.Eliminar_elemento)
        builder.setMessage(R.string.Desea_eliminar_la_gaveta)
        //set listeners for dialog buttons
        builder.setPositiveButton(
            R.string.Si
        ) { dialog, _ ->
                dialog.dismiss()
                deleteDrawerInternet(al_drawers[position].c_drawerS, position)
        }
        builder.setNegativeButton(R.string.No,
            DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })

        //create the alert dialog and show it
        builder.create().show()
    }

    private fun deleteDrawerInternet(drawerCode: String, position: Int) {

        repository.deleteDrawerS(drawerCode , al_drawers.get(position).fk_c_shelfS)

        al_drawers.removeAt(position)
        updateRecyclerAdapter()
        FancyToast.makeText(
            requireContext() ,
            getString(R.string.Operacion_realizada_con_exito) ,
            FancyToast.LENGTH_LONG ,
            FancyToast.SUCCESS ,
            false
        ).show()

    }


    fun setOpenDrawerSListener(openDrawerS: Fragment_Drawers.OpenDrawerS){
        this.openDrawer = openDrawerS
    }

    interface OpenDrawerS{
        fun onDrawerSClicked(code : String)
    }

}
