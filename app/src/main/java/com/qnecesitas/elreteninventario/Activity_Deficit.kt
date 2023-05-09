package com.qnecesitas.elreteninventario

import android.content.SharedPreferences
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

    //RadioButton
    private var selectButton = "Almacén"

    //SharedPreference
    private lateinit var shared: SharedPreferences
    private lateinit var sharedEditor: SharedPreferences.Editor
    private val FILTER_CODE = "filter"
    private val SELECT_BUTTON_CODE = "selectButton"

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

        //Shared
        shared = applicationContext.getSharedPreferences("ElReten", 0)
        sharedEditor = shared.edit()
        filter = shared.getInt(FILTER_CODE, 10)
        selectButton = shared.getString(SELECT_BUTTON_CODE, "Almacén").toString()
        binding.tiet.setText(filter.toString())
        if(selectButton == "Almacén"){
            binding.rbAlmacen.toggle()
        }else{
            binding.rbMostrador.toggle()
        }


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

            loadRecyclerInfo()
        }
        return false
    }


    private fun loadRecyclerInfo() {
        if (binding.tiet.text.toString().isNotEmpty()) {
            if (NetworkTools.isOnline(binding.root.context, false)) {
                binding.adRefresh.isRefreshing = true
                filter = binding.tiet.text.toString().toInt()
                binding.tiet.error = null

                val call = retrofitProductsImplS.fetchProductsDeficit(
                    Constants.PHP_TOKEN,
                    filter,
                    selectButton
                )
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
        }else{
            binding.tiet.error = getString(R.string.este_campo_no_debe_vacio)
        }
    }


    private fun updateRecyclerAdapter() {
        savePreference()
        if (al_deficitProduct.isEmpty()) {
            binding.adNotInfo.visibility = View.VISIBLE
            binding.adRecycler.visibility = View.GONE
            binding.adNotConnection.visibility = View.GONE
        } else {
            binding.adNotInfo.visibility = View.GONE
            binding.adRecycler.visibility = View.VISIBLE
            binding.adNotConnection.visibility = View.GONE
        }
        adapterR_DeficitProduct = AdapterR_DeficitProduct(al_deficitProduct,this)
        binding.adRecycler.adapter = adapterR_DeficitProduct

    }

    private fun savePreference(){
        sharedEditor.putInt(FILTER_CODE,filter)
        sharedEditor.putString(SELECT_BUTTON_CODE,selectButton)
        sharedEditor.apply()
    }


}