package com.qnecesitas.elreteninventario

import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.qnecesitas.elreteninventario.adapters.AdapterR_CounterProductAdd
import com.qnecesitas.elreteninventario.data.ModelCart
import com.qnecesitas.elreteninventario.data.ModelEditProductS
import com.qnecesitas.elreteninventario.data.ModelSale
import com.qnecesitas.elreteninventario.database.Repository
import com.qnecesitas.elreteninventario.databinding.FragmentCartBinding
import com.qnecesitas.elreteninventario.databinding.LiCartAceptBinding
import com.qnecesitas.elreteninventario.databinding.LiVoucherBinding
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar


class Fragment_Cart : Fragment() {

    //Binding
    private lateinit var binding: FragmentCartBinding
    private var li_cartAccept_binding: LiCartAceptBinding? = null
    private var li_voucher_binding: LiVoucherBinding? = null

    //Recycler
    private lateinit var alCart: ArrayList<ModelCart>
    private lateinit var adapterCart: AdapterR_CounterProductAdd

    //ListenerReload
    private var listenerReload: IReload? = null
    private var listenerDelete: IDeleteProduct? = null

    //Internet
    private lateinit var repository: Repository


    //Value
    private var lastModelSale: ModelSale? = null
    private var precioT: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater)


        //Recycler
        binding.recycler.setHasFixedSize(true)
        alCart = ArrayList()
        adapterCart = AdapterR_CounterProductAdd(alCart , requireContext())
        binding.recycler.adapter = adapterCart


        //Internet
        repository = Repository(requireActivity().application as ElRetenApplication)

        //Listener
        binding.btnDeleteProduct.setOnClickListener {
            alCart.clear()
            precioT = 0.0
            listenerReload?.onReload()
            updateRecyclerAdapter()
        }
        binding.btnAcceptProduct.setOnClickListener { liSendOrder() }

        updateRecyclerAdapter()

        return binding.root
    }


    /*Recycler operations
   * ---------------------------------
   * */
    fun addProduct(modelEditProductS: ModelEditProductS , amount: Int) {
        alCart.add(
            ModelCart(
                modelEditProductS.clone() ,
                amount
            )
        )
        precioT += amount * modelEditProductS.salePrice
        updateRecyclerAdapter()
    }


    private fun deleteProduct(position: Int) {
        listenerDelete?.onDeleteProduct(
            alCart[position].product.c_productS ,
            alCart[position].amount
        )
        precioT -= alCart[position].product.salePrice * alCart[position].amount
        alCart.removeAt(position)
        updateRecyclerAdapter()
    }

    private fun updateRecyclerAdapter() {
        if (alCart.isNotEmpty()) {
            alCart.sortBy { it.product.name }
        }

        if (alCart.isEmpty()) {
            alertNotElements(true)
        } else {
            alertNotElements(false)
        }
        adapterCart = AdapterR_CounterProductAdd(alCart , binding.root.context)

        binding.tvPrecioT.text = getString(R.string.PrecioTotal , precioT)

        adapterCart.setCancelListener(object : AdapterR_CounterProductAdd.RecyclerCancelListener {
            override fun onClick(position: Int) {
                deleteProduct(position)
            }

        })

        binding.recycler.adapter = adapterCart
    }

    fun isEmpty() = alCart.isEmpty()

    /*Alerts
   * ---------------------------------
   * */
    private fun alertNotElements(open: Boolean) {
        if (open) {
            binding.notInfo.visibility = View.VISIBLE
            binding.recycler.visibility = View.GONE
        } else {
            binding.notInfo.visibility = View.GONE
            binding.recycler.visibility = View.VISIBLE
        }
    }


    /*Internet
    * ---------------------------------
    * */
    private fun liSendOrder() {
        if (alCart.isNotEmpty()) {
            val inflater = LayoutInflater.from(binding.root.context)
            li_cartAccept_binding = LiCartAceptBinding.inflate(inflater)
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setView(li_cartAccept_binding?.root)
            val alertDialog = builder.create()

            val alType: ArrayList<String> =
                arrayListOf("Transferencia" , "Efectivo" , "Efectivo-Transferencia")
            val stringArrayAdapter =
                ArrayAdapter<String>(requireContext() , R.layout.list_item_spinner , alType)
            li_cartAccept_binding?.tietSpinner?.setAdapter<ArrayAdapter<String>>(stringArrayAdapter)


            li_cartAccept_binding?.tietSpinner?.onItemClickListener =
                OnItemClickListener { _ , _ , _ , _ ->
                    val selected = li_cartAccept_binding?.tietSpinner?.text.toString()
                    if (selected == alType[0] || selected == alType[2]) {
                        li_cartAccept_binding?.tilPago?.visibility = View.VISIBLE
                    } else {
                        li_cartAccept_binding?.tilPago?.visibility = View.GONE
                    }
                }

            //Listeners
            li_cartAccept_binding?.btnCancelar?.setOnClickListener(View.OnClickListener {
                alertDialog.dismiss()
            })

            li_cartAccept_binding?.btnAceptar?.setOnClickListener(View.OnClickListener {
                if (checkInfo()) {
                    val name = li_cartAccept_binding?.tietNombClient?.text.toString()
                    val discount = li_cartAccept_binding?.tietDescuento?.text.toString().toDouble()
                    val calendar = Calendar.getInstance()
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    val month = calendar.get(Calendar.MONTH) + 1
                    val year = calendar.get(Calendar.YEAR)
                    val type = li_cartAccept_binding?.tietSpinner?.text.toString()
                    val totalTransf = li_cartAccept_binding?.tietPago?.text.toString().toDouble()


                    //model
                    lastModelSale = ModelSale(
                        1 ,
                        name ,
                        makeProducts() ,
                        precioT ,
                        1.0 ,
                        discount ,
                        day ,
                        month ,
                        year ,
                        type ,
                        totalTransf
                    )


                    alertDialog.dismiss()
                    startProcess(name , discount , type , totalTransf)
                }
            })


            //Finalizado
            alertDialog.setCancelable(false)
            alertDialog.window!!.setGravity(Gravity.CENTER)
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.show()

        } else {
            FancyToast.makeText(
                requireContext() ,
                getString(R.string.no_elementos_carrito) ,
                FancyToast.LENGTH_LONG ,
                FancyToast.INFO ,
                false
            ).show()
        }
    }

    private fun liVoucher() {
        val inflater = LayoutInflater.from(binding.root.context)
        li_voucher_binding = LiVoucherBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_voucher_binding?.root)
        val alertDialog = builder.create()


        li_voucher_binding?.tvNombreX?.text = lastModelSale?.name
        li_voucher_binding?.tvFechaX?.text = getString(
            R.string.Fecha , lastModelSale?.day.toString() ,
            lastModelSale?.month.toString() , lastModelSale?.year.toString()
        )
        li_voucher_binding?.tvTotalPX?.text =
            getString(R.string.PrecioTotal , lastModelSale?.totalPrice?.toDouble())
        li_voucher_binding?.tvDiscountX?.text =
            getString(R.string.descuento_f , lastModelSale?.discount?.toDouble())
        li_voucher_binding?.tvPagox?.text = lastModelSale?.type
        li_voucher_binding?.tvProductX?.text = lastModelSale?.products?.replace("--n--" , "\n")
            ?.replace("--s--" , "   ")

        li_voucher_binding?.ivClose?.setOnClickListener {
            alertDialog.dismiss()
        }


        //Finalizado
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun startProcess(name: String , discount: Double , type: String , totalTransf: Double) {
        alCart.forEach {
            updateProductInternet(it)
        }
        addOrderInternet(name , discount , type , totalTransf)

    }

    private fun updateProductInternet(model: ModelCart) {
        lifecycleScope.launch {

            repository.alterAmountS(
                model.product.c_productS ,
                model.product.amount - model.amount
            )
        }
    }

    private fun addOrderInternet(
        nomb: String ,
        descuento: Double ,
        type: String ,
        totalTransf: Double
    ) {

        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        lifecycleScope.launch {


            repository.addOrder(
                nomb ,
                makeProducts() ,
                makeTotalPrice() ,
                makeTotalInv() ,
                descuento ,
                type ,
                totalTransf ,
                day ,
                month ,
                year
            )
        }

        FancyToast.makeText(
            requireContext() ,
            getString(R.string.Operacion_realizada_con_exito) ,
            FancyToast.LENGTH_LONG ,
            FancyToast.SUCCESS ,
            false
        ).show()
        alCart.clear()
        precioT = 0.0
        updateRecyclerAdapter()
        liVoucher()

    }


    /*Auxuliary
    * ---------------------------------
    * */

    private fun makeProducts(): String {
        var productsStr = ""
        var pos = 0
        for (product in alCart) {
            pos++
            productsStr += pos.toString() + product.product.name
            productsStr += "--n--"
            productsStr += "--s--Cantidad: " + product.amount
            productsStr += "--n--"
            productsStr += "--s--Código: " + product.product.c_productS
            productsStr += "--n--"
            productsStr += "--s--Precio total: "
            productsStr += (product.amount * product.product.salePrice).toString()
            productsStr += " CUP"
            productsStr += "--n--"
        }
        return productsStr
    }

    private fun makeTotalPrice(): Double {
        var price = 0.0
        for (it in alCart) {
            price += it.product.salePrice * it.amount
        }
        return price
    }

    private fun makeTotalInv(): Double {
        var inv = 0.0
        for (it in alCart) {
            inv += it.product.buyPrice
        }
        return inv
    }

    /*Listener
    *-------------------------
    */
    interface IReload {
        fun onReload()
    }

    fun setOnReloadListener(listenerReload: IReload) {
        this.listenerReload = listenerReload
    }

    interface IDeleteProduct {
        fun onDeleteProduct(code: String , amount: Int)
    }

    fun setListenerDelete(listenerDelete: IDeleteProduct) {
        this.listenerDelete = listenerDelete
    }

    private fun checkInfo(): Boolean {
        var amount = 0

        if (li_cartAccept_binding?.tietNombClient?.text?.trim()?.isNotEmpty() == true) {
            amount++
        } else {
            li_cartAccept_binding?.tilNombClient?.error =
                getString(R.string.este_campo_no_debe_vacio)
        }

        if (li_cartAccept_binding?.tietDescuento?.text?.trim()?.isNotEmpty() == true) {
            amount++
        } else {
            li_cartAccept_binding?.tilDescuento?.error =
                getString(R.string.este_campo_no_debe_vacio)
        }

        if (li_cartAccept_binding?.tietPago?.text?.trim()?.isNotEmpty() == true) {
            amount++
        } else {
            li_cartAccept_binding?.tietPago?.error = getString(R.string.este_campo_no_debe_vacio)
        }

        if (li_cartAccept_binding?.tietSpinner?.text?.trim()?.isNotEmpty() == true) {
            amount++
        } else {
            li_cartAccept_binding?.tietSpinner?.error = getString(R.string.este_campo_no_debe_vacio)
        }

        return amount == 4
    }


}