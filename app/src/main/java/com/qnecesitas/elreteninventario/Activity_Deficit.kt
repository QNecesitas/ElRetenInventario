package com.qnecesitas.elreteninventario

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.qnecesitas.elreteninventario.adapters.AdapterR_DeficitProduct
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.auxiliary.NetworkTools
import com.qnecesitas.elreteninventario.data.ModelEditProduct
import com.qnecesitas.elreteninventario.databinding.ActivityDeficitBinding
import com.qnecesitas.elreteninventario.network.RetrofitProductsImplS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Activity_Deficit : AppCompatActivity() {

    //Binding
    private lateinit var binding: ActivityDeficitBinding

    //Array
    private lateinit var al_deficitProduct: ArrayList<ModelEditProduct>
    private lateinit var adapterR_DeficitProduct: AdapterR_DeficitProduct

    //Internet
    private lateinit var retrofitProductsImplS: RetrofitProductsImplS

    //Filter
    private var filter = 10

    //spinner
    private var selectButton = "Almacén"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeficitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toolbar
        setSupportActionBar(binding.adToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.adToolbar.setNavigationOnClickListener { finish() }


        binding.tiet.setOnKeyListener { view, i, keyEvent ->
            eventKeyboard(
                view,
                keyEvent
            )
        }

        //RecyclerView
        binding.adRecycler.setHasFixedSize(true)
        al_deficitProduct = ArrayList()
        adapterR_DeficitProduct = AdapterR_DeficitProduct(al_deficitProduct, applicationContext)

        //Refresh
        retrofitProductsImplS = RetrofitProductsImplS()
        binding.adRefresh.setOnRefreshListener { loadRecyclerInfo() }

        //Radio Group
        binding.rg.setOnCheckedChangeListener { group, id ->
            when (id) {
                R.id.rb_almacen -> {
                    selectButton = "Almacén"
                    loadRecyclerInfo()
                }

                R.id.rb_mostrador -> {
                    selectButton = "Mostrador"
                    loadRecyclerInfo()
                }

                else -> {

                }
            }
        }

        //No Internet
        binding.adRetryConnection.setOnClickListener {
            loadRecyclerInfo()
        }


        loadRecyclerInfo()

    }

    private fun eventKeyboard(view: View, keyEvent: KeyEvent): Boolean {
        if (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
        return false
    }


    private fun loadRecyclerInfo() {
        if (NetworkTools.isOnline(binding.root.context, false)) {
            binding.adRefresh.isRefreshing = true
            if(binding.tiet.text.toString().isNotEmpty()){
                filter = binding.tiet.text.toString().toInt()
            }
            val call = retrofitProductsImplS.fetchProductsSDeficit(Constants.PHP_TOKEN,filter,selectButton)
            call.enqueue(object : Callback<ArrayList<ModelEditProduct>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelEditProduct>>,
                    response: Response<java.util.ArrayList<ModelEditProduct>>
                ) {
                    binding.adRefresh.isRefreshing = false
                    if (response.isSuccessful) {
                        binding.adNotConnection.visibility = View.GONE
                        binding.adRecycler.visibility = View.VISIBLE
                        binding.adNotInfo.visibility = View.GONE
                        al_deficitProduct = response.body()!!
                        updateRecyclerAdapter()
                    } else {
                        binding.adNotConnection.visibility = View.VISIBLE
                        binding.adRecycler.visibility = View.GONE
                        binding.adNotInfo.visibility = View.GONE
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelEditProduct>>,
                    t: Throwable
                ) {
                    binding.adRefresh.isRefreshing = false
                    binding.adNotInfo.visibility = View.GONE
                    binding.adNotConnection.visibility = View.VISIBLE
                    binding.adRecycler.visibility = View.GONE
                }
            })
        } else {
            binding.adNotInfo.visibility = View.GONE
            binding.adNotConnection.visibility = View.VISIBLE
            binding.adRecycler.visibility = View.GONE
        }
    }


    private fun updateRecyclerAdapter() {
        if (al_deficitProduct.isEmpty()) {
            binding.adNotInfo.visibility = View.VISIBLE
            binding.adRecycler.visibility = View.GONE
            binding.adNotConnection.visibility = View.GONE
        } else {
            binding.adNotInfo.visibility = View.GONE
            binding.adRecycler.visibility = View.VISIBLE
            binding.adNotConnection.visibility = View.GONE
        }
        adapterR_DeficitProduct = AdapterR_DeficitProduct(al_deficitProduct, binding.root.context)
        binding.adRecycler.adapter = adapterR_DeficitProduct

    }



}