package com.qnecesitas.elreteninventario

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.qnecesitas.elreteninventario.adapters.AdapterR_EditProduct
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.auxiliary.NetworkTools
import com.qnecesitas.elreteninventario.data.ModelEditProduct
import com.qnecesitas.elreteninventario.databinding.ActivityEditProductBinding
import com.qnecesitas.elreteninventario.network.RetrofitDrawersImplS
import com.qnecesitas.elreteninventario.network.RetrofitProductsImplS
import org.json.JSONArray
import org.json.JSONException

class Activity_EditProduct : AppCompatActivity() {
    //AddEditProduct
    private val amount = 0

    //Eliminiar
    private val auxPosEliminar = 0
    private val li_imagen_producto: ImageView? = null

    //Recycler
    private lateinit var binding: ActivityEditProductBinding
    private var recycler: RecyclerView? = null
    private lateinit var al_editProduct: ArrayList<ModelEditProduct>
    private lateinit var adapterR_editProducts: AdapterR_EditProduct


    //Internet
    private lateinit var retrofitProductsImplS: RetrofitProductsImplS
    private var selected_sessions: Int? = null
    private val ANCHO_DE_FOTO_A_SUBIR = 900
    private val ALTO_DE_FOTO_A_SUBIR = 900
    private val INTENT_RESULT_GALERIA0 = 5
    val INTENT_RESULT_GALERIA1 = 7
    val INTENT_RESULT_GALERIA2 = 9
    private val PERMISO_GALERIA = 3
    private val uriLLegadaRecortada: Uri? = null


    //Image a subir
    private val imageArecortar = 0
    private val lastNameSelected: String? = null


    //Search
    private val drawer: DrawerLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_product)

        selected_sessions = intent.getIntExtra("category", 0)


        //Toolbar
        setSupportActionBar(binding.AEPToolbar)

        //Refresh
        binding.refresh.setOnRefreshListener( object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                binding.refresh.isRefreshing = true
                loadRecyclerInfo()
                binding.refresh.isRefreshing = false
            }
        } )


        //RecyclerView
        binding.AEPRecycler.setHasFixedSize(true)
        al_editProduct = ArrayList()
        adapterR_editProducts = AdapterR_EditProduct(al_editProduct,applicationContext)


        //Search
        binding.AEPSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                adapterR_editProducts.getFilter()?.filter(s)
                return true
            }
        })

        //Internet
        binding.fdRetryConnection.setOnClickListener {
            binding.refresh.isRefreshing = true
            loadRecyclerInfo()
        }
        binding.refresh.isRefreshing = true
        loadRecyclerInfo()

    }

    //Download
    private fun loadMainInternetInfo() {
        if (NetworkTools.isOnline(this, false)) {
            binding.refresh.isRefreshing = true
            loadRecyclerInfo()
        } else {
            binding.fdNotConnection.visibility = View.VISIBLE
            binding.AEPRecycler.visibility = View.GONE
            binding.fdNotInfo.visibility = View.GONE
        }
    }
    private fun loadRecyclerInfo() {
        if (NetworkTools.isOnline(this, false)) {
          val call = retrofitProductsImplS.fetchProducts(Constants.PHP_TOKEN,al)
        } else {
            binding.fdNotConnection.visibility = View.VISIBLE
            binding.AEPRecycler.visibility = View.GONE
            binding.fdNotInfo.visibility = View.GONE
        }
        binding.refresh.isRefreshing = false
    }


}