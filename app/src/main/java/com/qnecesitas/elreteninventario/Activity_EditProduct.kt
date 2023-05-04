package com.qnecesitas.elreteninventario

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.qnecesitas.elreteninventario.adapters.AdapterR_EditProduct
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.auxiliary.FragmentsInfo
import com.qnecesitas.elreteninventario.auxiliary.NetworkTools
import com.qnecesitas.elreteninventario.data.ModelEditProduct
import com.qnecesitas.elreteninventario.databinding.ActivityEditProductBinding
import com.qnecesitas.elreteninventario.databinding.LiAddProductBinding
import com.qnecesitas.elreteninventario.databinding.LiEditProductBinding
import com.qnecesitas.elreteninventario.databinding.LiInfoProductBinding
import com.qnecesitas.elreteninventario.network.RetrofitProductsImplS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Activity_EditProduct : AppCompatActivity() {

    private lateinit var binding: ActivityEditProductBinding
    //Add Session
    private lateinit var li_add_binding: LiAddProductBinding
    //Info Product
    private lateinit var li_info_binding: LiInfoProductBinding
    //Edit Product
    private lateinit var li_edit_binding: LiEditProductBinding

    private lateinit var al_editProduct: ArrayList<ModelEditProduct>
    private lateinit var adapterR_editProducts: AdapterR_EditProduct

    //Internet
    private lateinit var retrofitProductsImplS: RetrofitProductsImplS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofitProductsImplS = RetrofitProductsImplS()

        //Toolbar
        setSupportActionBar(binding.aepToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.aepToolbar.setNavigationOnClickListener { finish() }

        //Add Button
        if (FragmentsInfo.LAST_CODE_SESSION_SENDED == "no") binding.aepFabAdd.visibility = View.GONE;
        else binding.aepFabAdd.setOnClickListener { click_add() }

        //Refresh
        binding.aepRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                binding.aepRefresh.isRefreshing = true
                loadRecyclerInfo()
                binding.aepRefresh.isRefreshing = false
            }
        })

        //RecyclerView
        binding.aepRecycler.setHasFixedSize(true)
        al_editProduct = ArrayList()
        adapterR_editProducts = AdapterR_EditProduct(al_editProduct, applicationContext)

        //Search
        binding.aepSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                adapterR_editProducts.getFilter()?.filter(s)
                return true
            }
        })

        //Internet
        binding.aepRetryConnection.setOnClickListener {
            binding.aepRefresh.isRefreshing = true
            loadRecyclerInfo()
        }
        loadRecyclerInfo()
    }

    private fun loadRecyclerInfo() {
        binding.aepRefresh.isRefreshing = true
        if (NetworkTools.isOnline(binding.root.context, false)) {

            val call =
                if (FragmentsInfo.LAST_CODE_SESSION_SENDED == "no") retrofitProductsImplS.fetchProductsSAll(
                    Constants.PHP_TOKEN
                )
                else retrofitProductsImplS.fetchProducts(
                    Constants.PHP_TOKEN,
                    FragmentsInfo.LAST_CODE_SESSION_SENDED
                )
            call.enqueue(object : Callback<ArrayList<ModelEditProduct>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelEditProduct>>,
                    response: Response<java.util.ArrayList<ModelEditProduct>>
                ) {
                    if (response.isSuccessful) {
                        binding.aepNotConnection.visibility = View.GONE
                        binding.aepRecycler.visibility = View.VISIBLE
                        binding.aepNotInfo.visibility = View.GONE
                        al_editProduct = response.body()!!
                        updateRecyclerAdapter()
                    } else {
                        binding.aepNotConnection.visibility = View.VISIBLE
                        binding.aepRecycler.visibility = View.GONE
                        binding.aepNotInfo.visibility = View.GONE
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelEditProduct>>,
                    t: Throwable
                ) {
                    binding.aepNotConnection.visibility = View.VISIBLE
                    binding.aepRecycler.visibility = View.GONE
                    binding.aepNotInfo.visibility = View.GONE
                }
            })
        } else {
            binding.aepNotConnection.visibility = View.VISIBLE
            binding.aepRecycler.visibility = View.GONE
            binding.aepNotInfo.visibility = View.GONE
        }
        binding.aepRefresh.isRefreshing = false
    }

    private fun updateRecyclerAdapter() {
        if (al_editProduct.isEmpty()) {
            binding.aepNotInfo.visibility = View.VISIBLE
            binding.aepRecycler.visibility = View.GONE
            binding.aepNotConnection.visibility = View.GONE
        } else {
            binding.aepNotInfo.visibility = View.GONE
            binding.aepRecycler.visibility = View.VISIBLE
            binding.aepNotConnection.visibility = View.GONE
        }
        adapterR_editProducts = AdapterR_EditProduct(al_editProduct, binding.root.context)

        adapterR_editProducts.setRecyclerOnClickListener(object :
            AdapterR_EditProduct.RecyclerClickListener {
            override fun onClick(position: Int) {
                li_infoProduct(position);
            }

        })
        binding.aepRecycler.adapter = adapterR_editProducts
    }

    private fun li_infoProduct(position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_info_binding = LiInfoProductBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_info_binding.root)
        val alertDialog = builder.create()


        //Finalizado
        builder.setCancelable(true)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun li_editProduct(position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_edit_binding = LiEditProductBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_add_binding.root)
        val alertDialog = builder.create()


        //Finalizado
        builder.setCancelable(true)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun click_add() {
        li_newProduct()
    }

    private fun li_newProduct() {
        val inflater = LayoutInflater.from(binding.root.context)
        li_add_binding = LiAddProductBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_add_binding.root)
        val alertDialog = builder.create()

        //Finalizado
        builder.setCancelable(true)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }
}