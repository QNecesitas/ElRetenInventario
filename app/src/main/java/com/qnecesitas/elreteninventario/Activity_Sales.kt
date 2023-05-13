package com.qnecesitas.elreteninventario

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import com.google.android.material.snackbar.Snackbar
import com.qnecesitas.elreteninventario.adapters.AdapterR_CounterProductShow
import com.qnecesitas.elreteninventario.adapters.AdapterR_Sales
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.auxiliary.NetworkTools
import com.qnecesitas.elreteninventario.data.ModelEditProduct
import com.qnecesitas.elreteninventario.data.ModelSale
import com.qnecesitas.elreteninventario.databinding.ActivitySalesBinding
import com.qnecesitas.elreteninventario.network.RetrofitSalesImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class Activity_Sales : AppCompatActivity() {

    //Binding
    private lateinit var binding: ActivitySalesBinding

    //Recycler
    private lateinit var adapterrSales: AdapterR_Sales
    private lateinit var al_sales: ArrayList<ModelSale>

    //Internet
    private lateinit var retrofitImp: RetrofitSalesImpl

    //Date
    private var year = 0
    private var month = 0
    private var day = 0

    //Refresh
    private var refre = "All"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        //Spinner
        val alSpinner = arrayListOf(getString(R.string.todo),getString(R.string.Dia),getString(R.string.Mes), getString(R.string.Anno))
        val adapterSpinner = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, alSpinner);
        binding.spinner.adapter = adapterSpinner
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, long: Long) {
                val value: String = parent?.getItemAtPosition(position).toString()
                when(value){
                    "Día" -> loadRecyclerInfoDay()
                    "Mes" -> loadRecyclerInfoMonth()
                    "Año" -> loadRecyclerInfoYear()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        //SearchView
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterrSales.getFilter()?.filter(newText)
                return false
            }

        })
        binding.ivIconSearch.setOnClickListener(View.OnClickListener {
            binding.acClSearch.visibility = View.VISIBLE
            binding.ivIconSearch.visibility = View.GONE
        })
        binding.ivCloseSearch.setOnClickListener(View.OnClickListener {
            binding.acClSearch.visibility = View.GONE
            binding.ivIconSearch.visibility = View.VISIBLE
        })

        //Refresh
        binding.refresh.setOnRefreshListener {
            when(refre){
                "All" -> loadRecyclerInfoAll()
                "Year" -> loadRecyclerInfoYear()
                "Month" -> loadRecyclerInfoMonth()
                "Day" -> loadRecyclerInfoDay()
            }
        }

        //Date
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)

        //Recycler
        al_sales = ArrayList()
        adapterrSales = AdapterR_Sales(al_sales,this)
        binding.rvSales.adapter = adapterrSales


        //Internet
        retrofitImp = RetrofitSalesImpl()
        binding.retryConnection.setOnClickListener{
            loadRecyclerInfoAll()
        }

        //Recycler
        loadRecyclerInfoAll()
    }


    /*Initial thread
    * ---------------------------------
    * */
    private fun loadRecyclerInfoAll() {
        if (NetworkTools.isOnline(binding.root.context, false)) {
            binding.refresh.isRefreshing = true
            refre = "All"
            val call = retrofitImp.fetchOrdersAll(Constants.PHP_TOKEN)
            call.enqueue(object : Callback<ArrayList<ModelSale>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelSale>>,
                    response: Response<java.util.ArrayList<ModelSale>>
                ) {
                    binding.refresh.isRefreshing = false
                    if (response.isSuccessful) {
                        alertNotInternet(false)
                        al_sales = response.body()!!
                        updateRecyclerAdapter()
                    } else {
                        alertNotInternet(true)
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelSale>>,
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

    private fun loadRecyclerInfoYear() {
        if (NetworkTools.isOnline(binding.root.context, false)) {
            binding.refresh.isRefreshing = true
            refre = "Year"
            val call = retrofitImp.fetchOrdersY(Constants.PHP_TOKEN,year)
            call.enqueue(object : Callback<ArrayList<ModelSale>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelSale>>,
                    response: Response<java.util.ArrayList<ModelSale>>
                ) {
                    binding.refresh.isRefreshing = false
                    if (response.isSuccessful) {
                        alertNotInternet(false)
                        al_sales = response.body()!!
                        updateRecyclerAdapter()
                    } else {
                        alertNotInternet(true)
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelSale>>,
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

    private fun loadRecyclerInfoMonth() {
        if (NetworkTools.isOnline(binding.root.context, false)) {
            binding.refresh.isRefreshing = true
            refre = "Month"
            val call = retrofitImp.fetchOrdersM(Constants.PHP_TOKEN,year,month)
            call.enqueue(object : Callback<ArrayList<ModelSale>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelSale>>,
                    response: Response<java.util.ArrayList<ModelSale>>
                ) {
                    binding.refresh.isRefreshing = false
                    if (response.isSuccessful) {
                        alertNotInternet(false)
                        al_sales = response.body()!!
                        updateRecyclerAdapter()
                    } else {
                        alertNotInternet(true)
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelSale>>,
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

    private fun loadRecyclerInfoDay() {
        if (NetworkTools.isOnline(binding.root.context, false)) {
            binding.refresh.isRefreshing = true
            refre = "Day"
            val call = retrofitImp.fetchOrdersD(Constants.PHP_TOKEN,year,month,day)
            call.enqueue(object : Callback<ArrayList<ModelSale>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelSale>>,
                    response: Response<java.util.ArrayList<ModelSale>>
                ) {
                    binding.refresh.isRefreshing = false
                    if (response.isSuccessful) {
                        alertNotInternet(false)
                        al_sales = response.body()!!
                        updateRecyclerAdapter()
                    } else {
                        alertNotInternet(true)
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelSale>>,
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
        if (al_sales.isEmpty()) {
            alertNotElements(true)
        } else {
            alertNotElements(false)
        }

        adapterrSales = AdapterR_Sales(al_sales,this)


        binding.rvSales.adapter = adapterrSales

    }






    /*Alerts
    * ---------------------------------
    * */
    private fun alertNotElements(open: Boolean) {
        if (open) {
            binding.notInfo.visibility = View.VISIBLE
            binding.rvSales.visibility = View.GONE
            binding.notConnection.visibility = View.GONE
        } else {
            binding.notInfo.visibility = View.GONE
            binding.rvSales.visibility = View.VISIBLE
            binding.notConnection.visibility = View.GONE
        }
    }
    private fun alertNotInternet(open: Boolean) {
        if (open) {
            binding.notConnection.visibility = View.VISIBLE
            binding.rvSales.visibility = View.GONE
            binding.notInfo.visibility = View.GONE
        } else {
            binding.notConnection.visibility = View.GONE
            binding.rvSales.visibility = View.VISIBLE
            binding.notInfo.visibility = View.GONE
        }
    }


    //Date
    fun showDateDialog(){

        val datePickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{ view, year, monthOfYear,dayOfMonth ->
            sendDate(year,monthOfYear,dayOfMonth)
        },year,month,day)

        datePickerDialog.show()

    }

    fun sendDate(year: Int, month: Int, day: Int){
        filterDate()
    }

    fun filterDate(){

    }


}