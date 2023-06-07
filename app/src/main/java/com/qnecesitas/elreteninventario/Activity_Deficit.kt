package com.qnecesitas.elreteninventario

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.qnecesitas.elreteninventario.adapters.AdapterR_DeficitProduct
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelEditProductS
import com.qnecesitas.elreteninventario.database.Repository
import com.qnecesitas.elreteninventario.databinding.ActivityDeficitBinding
import com.qnecesitas.elreteninventario.network.RetrofitProductsImplS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Activity_Deficit : AppCompatActivity() {

    //Binding
    private lateinit var binding: ActivityDeficitBinding

    //Array
    private lateinit var al_deficitProduct: ArrayList<ModelEditProductS>
    private lateinit var adapterR_DeficitProduct: AdapterR_DeficitProduct

    //Internet
    private lateinit var repository: Repository

    //Filter
    private var filter = 10

    //RadioButton
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


        //RecyclerView
        binding.adRecycler.setHasFixedSize(true)
        al_deficitProduct = ArrayList()
        adapterR_DeficitProduct = AdapterR_DeficitProduct(al_deficitProduct , applicationContext)

        //Refresh
        repository = Repository(application as ElRetenApplication)
        binding.adRefresh.setOnRefreshListener { loadRecyclerInfo() }


        //Radio Group
        binding.rg.setOnCheckedChangeListener { _ , id ->
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



        loadRecyclerInfo()

    }

    private fun eventKeyboard(view: View , keyEvent: KeyEvent): Boolean {
        if (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken , 0)

            loadRecyclerInfo()
        }
        return false
    }


    private fun loadRecyclerInfo() {
        /*al_deficitProduct = repository.fetchProductsDeficit(
                selectButton
        ) TODO  */
        binding.adRecycler.visibility = View.VISIBLE
        binding.adNotInfo.visibility = View.GONE
        updateRecyclerAdapter()
    }

    private fun updateRecyclerAdapter() {
        if (al_deficitProduct.isEmpty()) {
            binding.adNotInfo.visibility = View.VISIBLE
            binding.adRecycler.visibility = View.GONE
        } else {
            binding.adNotInfo.visibility = View.GONE
            binding.adRecycler.visibility = View.VISIBLE
        }
        adapterR_DeficitProduct = AdapterR_DeficitProduct(al_deficitProduct , this)
        binding.adRecycler.adapter = adapterR_DeficitProduct

    }

}