package com.qnecesitas.elreteninventario

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.qnecesitas.elreteninventario.adapters.AdapterR_CounterProductAdd
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.auxiliary.NetworkTools
import com.qnecesitas.elreteninventario.data.ModelCart
import com.qnecesitas.elreteninventario.data.ModelEditProduct
import com.qnecesitas.elreteninventario.databinding.FragmentCartBinding
import com.qnecesitas.elreteninventario.network.RetrofitCartImpl
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragment_Cart : Fragment() {

    //Binding
    private lateinit var binding: FragmentCartBinding

    //Recycler
    private lateinit var alCart: ArrayList<ModelCart>
    private lateinit var adapterCart: AdapterR_CounterProductAdd

    //ListenerReload
    private var listenerReload : IReload? = null
    private var listenerDelete : IDeleteProduct? = null

    //Internet
    private lateinit var retrofitCart: RetrofitCartImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater)


        //Recycler
        binding.recycler.setHasFixedSize(true)
        alCart = ArrayList()
        adapterCart = AdapterR_CounterProductAdd(alCart, requireContext())
        binding.recycler.adapter = adapterCart


        //Internet
        retrofitCart = RetrofitCartImpl()

        //Listener
        binding.ivDeleteAll.setOnClickListener{
            alCart.clear()
            listenerReload?.onReload()
            updateRecyclerAdapter()
        }
        binding.btnAcceptProduct.setOnClickListener{sendOrder()}

        updateRecyclerAdapter()

        return binding.root
    }


    /*Recycler operations
   * ---------------------------------
   * */
    fun addProduct(modelEditProduct: ModelEditProduct, amount: Int) {
        alCart.add(
            ModelCart(
                modelEditProduct.clone(),
                amount
            )
        )
        updateRecyclerAdapter()
    }


    private fun deleteProduct(position: Int) {
        listenerDelete?.onDeleteProduct(alCart[position].product.c_productS,alCart[position].amout)
        alCart.removeAt(position)
        updateRecyclerAdapter()
    }

    private fun updateRecyclerAdapter() {
        if (alCart.isEmpty()) {
            alertNotElements(true)
        } else {
            alertNotElements(false)
        }
        adapterCart = AdapterR_CounterProductAdd(alCart, binding.root.context)

        adapterCart.setCancelListener(object : AdapterR_CounterProductAdd.RecyclerClickListener {
            override fun onClick(position: Int) {
                deleteProduct(position)
            }

        })

        binding.recycler.adapter = adapterCart
    }


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


    /*Other Listeners
    * ---------------------------------
    * */
    private fun sendOrder() {
        if (alCart.isNotEmpty()) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.registrar_venta))
                .setMessage(getString(R.string.tiene_seguridad_de_registrar))
                .setCancelable(false)
                .setPositiveButton(R.string.Aceptar) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    addOrderInternet()
                }
                .setNegativeButton(R.string.cancelar){dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
        } else {
            FancyToast.makeText(
                requireContext(),
                getString(R.string.no_elementos_carrito),
                FancyToast.LENGTH_LONG,
                FancyToast.INFO,
                false
            );
        }
    }

    private fun addOrderInternet(){
        if(NetworkTools.isOnline(binding.root.context, false)){
            binding.progress.visibility = View.VISIBLE
            val call = retrofitCart.addOrder(Constants.PHP_TOKEN,makeProducts(),makeTotalPrice(),"añadir aqui telefono")
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    binding.progress.visibility = View.GONE
                    if (response.isSuccessful) {
                        FancyToast.makeText(
                            requireContext(),
                            getString(R.string.Operacion_realizada_con_exito),
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false
                        ).show()
                        alCart.clear()
                        updateRecyclerAdapter()
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

                override fun onFailure(call: Call<String>, t: Throwable) {
                    binding.progress.visibility = View.GONE
                    FancyToast.makeText(
                        requireContext(),
                        getString(R.string.Revise_su_conexion),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false
                    ).show()
                }
            })


        }else{
            FancyToast.makeText(
                requireContext(),
                getString(R.string.Revise_su_conexion),
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,
                false
            ).show()
        }


    }

    /*Auxuliary
    * ---------------------------------
    * */
    private fun makeProducts(): String{
        var productsStr = ""
        var pos = 0
        for(product in alCart){
            pos++
            productsStr += pos.toString() + product.product.name
            productsStr += "--n--"
            productsStr += "--s--Cantidad: " + product.amout
            productsStr += "--n--"
            productsStr += "--s--Precio total: "
            productsStr += (product.amout * product.product.salePrice).toString()
            productsStr += " CUP"
            productsStr += "--n--"
        }
        return productsStr
    }

    private fun makeTotalPrice(): Double{
        var price = 0.0
        for (it in alCart){
            price += it.product.salePrice
        }
        return price
    }

    /*Listener
    *-------------------------
    */
    interface IReload{
        fun onReload()
    }

    fun setOnReloadListener(listenerReload : IReload){
        this.listenerReload = listenerReload
    }

    interface IDeleteProduct{
        fun onDeleteProduct(code: String, amount: Int)
    }

    fun setListenerDelete(listenerDelete: IDeleteProduct){
        this.listenerDelete = listenerDelete
    }

}