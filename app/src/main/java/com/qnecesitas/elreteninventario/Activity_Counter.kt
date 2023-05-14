package com.qnecesitas.elreteninventario

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qnecesitas.elreteninventario.adapters.AdapterR_CounterProductShow
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.auxiliary.NetworkTools
import com.qnecesitas.elreteninventario.data.ModelEditProduct
import com.qnecesitas.elreteninventario.databinding.ActivityCounterBinding
import com.qnecesitas.elreteninventario.databinding.LiAddCounterBinding
import com.qnecesitas.elreteninventario.databinding.LiShowProductBinding
import com.qnecesitas.elreteninventario.network.RetrofitCounterImpl
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Activity_Counter : AppCompatActivity() {

    //Recyclers
    private lateinit var adapterCounter: AdapterR_CounterProductShow
    private lateinit var alCounter: ArrayList<ModelEditProduct>

    //Binding
    private lateinit var binding: ActivityCounterBinding

    //Add counter
    private lateinit var li_add_counter_binding: LiAddCounterBinding

    //Fragment
    lateinit var fragmentManager: FragmentManager

    //Internet
    private lateinit var retrofitImp: RetrofitCounterImpl

    //Fragment
    lateinit var fragment_carrito: Fragment_Cart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCounterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toolbar
        setSupportActionBar(binding.acToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.acToolbar.setNavigationOnClickListener { finish() }

        //SearchView
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterCounter.getFilter()?.filter(newText)
                return false
            }

        })

        //Fragments
        showFragment()

        //Recycler
        alCounter = ArrayList()
        adapterCounter = AdapterR_CounterProductShow(alCounter, this)
        binding.rvProductsShow.adapter = adapterCounter

        //Internet
        retrofitImp = RetrofitCounterImpl()
        binding.retryConnection.setOnClickListener {
            loadRecyclerInfo()
        }
        binding.refresh.setOnRefreshListener { loadRecyclerInfo() }

        //Start
        loadRecyclerInfo()

    }


    /*Initial thread
    * ---------------------------------
    * */
    private fun loadRecyclerInfo() {
        if (NetworkTools.isOnline(binding.root.context, false)) {
            binding.refresh.isRefreshing = true

            val call = retrofitImp.fetchProducts(Constants.PHP_TOKEN)
            call.enqueue(object : Callback<ArrayList<ModelEditProduct>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelEditProduct>>,
                    response: Response<java.util.ArrayList<ModelEditProduct>>
                ) {
                    binding.refresh.isRefreshing = false
                    if (response.isSuccessful) {
                        alertNotInternet(false)
                        alCounter = response.body()!!
                        updateRecyclerAdapter()
                    } else {
                        alertNotInternet(true)
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelEditProduct>>,
                    t: Throwable
                ) {
                    alertNotInternet(true)
                    binding.refresh.isRefreshing = false
                }
            })
        } else {
            alertNotInternet(true)
        }
    }

    private fun updateRecyclerAdapter() {
        if (alCounter.isEmpty()) {
            alertNotElements(true)
        } else {
            alertNotElements(false)
        }

        adapterCounter = AdapterR_CounterProductShow(alCounter, binding.root.context)

        //Listeners
        adapterCounter.setClickListener(object : AdapterR_CounterProductShow.RecyclerClickListener {
            override fun onClick(position: Int) {
                rootRecyclerClick(position)
            }
        })
        adapterCounter.setClickInfoListener(object :
            AdapterR_CounterProductShow.RecyclerClickListener {
            override fun onClick(position: Int) {
                infoRecyclerClick(position)
            }

        })
        adapterCounter.setClickTransfListener(object :
            AdapterR_CounterProductShow.RecyclerClickListener {
            override fun onClick(position: Int) {
                transfRecyclerClick(position)
            }

        })

        binding.rvProductsShow.adapter = adapterCounter
    }

    /***TODO 15 min
     * TODO _Mostrar el fragment en el Frame inferior
     * TODO _Revisa metodos showFragment... en Activity Editar producto
     */
    private fun showFragment() {
        fragmentManager = supportFragmentManager
        fragment_carrito = Fragment_Cart()
        fragment_carrito.setOnReloadListener(object : Fragment_Cart.IReload{
            override fun onReload() {
                loadRecyclerInfo()
            }

        })
        fragment_carrito.setListenerDelete(object : Fragment_Cart.IDeleteProduct{
            override fun onDeleteProduct(code: String, amount: Int ) {
                for(model in alCounter){
                   if(model.c_productS == code){
                       model.amount += amount
                       updateRecyclerAdapter()
                   }
                }
            }
        })
        fragmentManager.beginTransaction()
            .replace(binding.flCart.id, fragment_carrito)
            .commit()
    }

    /*Alerts
    * ---------------------------------
    * */
    private fun alertNotElements(open: Boolean) {
        if (open) {
            binding.notInfo.visibility = View.VISIBLE
            binding.rvProductsShow.visibility = View.GONE
            binding.notConnection.visibility = View.GONE
        } else {
            binding.notInfo.visibility = View.GONE
            binding.rvProductsShow.visibility = View.VISIBLE
            binding.notConnection.visibility = View.GONE
        }
    }

    private fun alertNotInternet(open: Boolean) {
        if (open) {
            binding.notConnection.visibility = View.VISIBLE
            binding.rvProductsShow.visibility = View.GONE
            binding.notInfo.visibility = View.GONE
        } else {
            binding.notConnection.visibility = View.GONE
            binding.rvProductsShow.visibility = View.VISIBLE
            binding.notInfo.visibility = View.GONE
        }
    }

    /*Listeners
    * ---------------------------------
    * */
    private fun rootRecyclerClick(position: Int) {
        if(alCounter[position].amount == 0){
            FancyToast.makeText(this,getString(R.string.no_puede_acceder_con_0),FancyToast.LENGTH_LONG,FancyToast.WARNING,false).show()
        }else{
            li_cant(position)
        }
    }

    private fun infoRecyclerClick(position: Int) {
        li_infoProduct(position)
    }

    private fun transfRecyclerClick(position: Int) {
        //TODO("Not implemented yet")
    }

    /*Inflated Layouts
    * ---------------------------------
    * */
    private fun li_infoProduct(position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        val li_info_binding = LiShowProductBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_info_binding.root)
        val alertDialog = builder.create()

        //Init
        val name = getString(R.string.Nombre_Info, alCounter[position].name)
        val code = getString(R.string.Codigo_Info, alCounter[position].c_productS)
        val amount = getString(R.string.Cantidad_Info, alCounter[position].amount)
        val salePrice = getString(R.string.PrecioV_Info, alCounter[position].salePrice)
        val descr = getString(R.string.Descripcion_Info, alCounter[position].descr)
        val codeImage = alCounter[position].c_productS

        //Fill out
        li_info_binding.tvName.text = name
        li_info_binding.tvCode.text = code
        li_info_binding.tvAmount.text = amount
        li_info_binding.tvPrice.text = salePrice
        li_info_binding.tvDesc.text = descr
        li_info_binding.ivImageProduct.let {
            Glide.with(applicationContext)
                .load(Constants.PHP_IMAGES + "P_" + codeImage + ".jpg")
                .error(R.drawable.widgets)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(it)
        }

        //Listeners
        li_info_binding.close.setOnClickListener { alertDialog.dismiss() }

        //Finalizado
        builder.setCancelable(true)
        alertDialog.window?.setGravity(Gravity.CENTER)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    /***TODO
     * TODO _Hacer funcionalidad de sus botones  -------------10 min
     * TODO _Revisar el li_cantidad de Edit_Product y ver todas las comprobaciones que hay que hacer --------------10 min
     * TODO _Hacer las comprobaciones para cuando se exceda del numero posible(con la resta) -------------------10 min
     * TODO _Al aceptar, que se envien al recycler---------------------------------------------------5 min
     * TODO _Mostrar si la cantidad se excedio de la maxima un AlerDialog(llamando al metodo alertMaxAmount) --------5 min
     */
    private fun li_cant(position: Int) {

        val inflater = LayoutInflater.from(binding.root.context)
        li_add_counter_binding = LiAddCounterBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_add_counter_binding.root)
        val alertDialog = builder.create()

        //Filling and listeners
        var currentAmount: Int = alCounter.get(position).amount
        val maxAmount = currentAmount

        li_add_counter_binding.etCantidad.setText(currentAmount.toString())

        li_add_counter_binding.btnMore.setOnClickListener(View.OnClickListener {
            if (currentAmount != maxAmount) {
                currentAmount++
                li_add_counter_binding.etCantidad.setText(currentAmount.toString())
            }
        })

        li_add_counter_binding.btnLess.setOnClickListener(View.OnClickListener {
            if (currentAmount != 1) {
                currentAmount--
                li_add_counter_binding.etCantidad.setText(currentAmount.toString())
            }
        })

        li_add_counter_binding.etCantidad.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if(li_add_counter_binding.etCantidad.text.toString().isNotEmpty()){
                    if (li_add_counter_binding.etCantidad.text.toString() == "0") {
                        currentAmount = 1
                        li_add_counter_binding.etCantidad.setText(currentAmount.toString())
                    } else if (li_add_counter_binding.etCantidad.text.toString()
                            .toInt() > maxAmount
                    ) {
                        currentAmount = maxAmount
                        li_add_counter_binding.etCantidad.setText(currentAmount.toString())
                    } else {
                        currentAmount = li_add_counter_binding.etCantidad.text.toString().toInt()
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {}
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        })

        li_add_counter_binding.tvAccept.setOnClickListener {
            if (li_add_counter_binding.etCantidad.text.toString() == "") {
                li_add_counter_binding.etCantidad.error =
                    getString(R.string.este_campo_no_debe_vacio)
            } else {
                alertDialog.dismiss()
                fragment_carrito.addProduct(alCounter[position], currentAmount)
                alCounter[position].amount = maxAmount - currentAmount
                updateRecyclerAdapter()
            }
        }

        li_add_counter_binding.tvCancel.setOnClickListener {
            alertDialog.dismiss()
        }


        //Finalizado
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

    }


}