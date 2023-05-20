package com.qnecesitas.elreteninventario

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.qnecesitas.elreteninventario.adapters.AdapterR_Sales
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.auxiliary.NetworkTools
import com.qnecesitas.elreteninventario.data.ModelSale
import com.qnecesitas.elreteninventario.databinding.ActivitySalesBinding
import com.qnecesitas.elreteninventario.databinding.LiAddProductBinding
import com.qnecesitas.elreteninventario.databinding.LiDateYBinding
import com.qnecesitas.elreteninventario.databinding.LiDateYmBinding
import com.qnecesitas.elreteninventario.databinding.LiDateYmdBinding
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
    private var dateSelected = "Todo"
    private var year = 0
    private var month = 0
    private var day = 0

    //ON CREATE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }


        //Date
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        binding.ivDate.setOnClickListener(View.OnClickListener {
            select_datePick()
        })


        //Spinner
        val alSpinner = arrayListOf(getString(R.string.todo),getString(R.string.Dia),getString(R.string.Mes), getString(R.string.Anno))
        val adapterSpinner = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, alSpinner);
        binding.spinner.adapter = adapterSpinner
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, long: Long) {
                val value: String = parent?.getItemAtPosition(position).toString()
                dateSelected = value
                when(value) {
                    "Todo"-> {
                        binding.ivDate.visibility = View.GONE
                        binding.tvDate.visibility = View.GONE
                        loadRecyclerInfoAll()
                    }
                    "Día" ->{
                        binding.ivDate.visibility = View.VISIBLE
                        binding.tvDate.visibility = View.VISIBLE
                        loadRecyclerInfoDay()
                    }
                    "Mes" ->{
                        binding.ivDate.visibility = View.VISIBLE
                        binding.tvDate.visibility = View.VISIBLE
                        loadRecyclerInfoMonth()
                    }
                    "Año" ->{
                        binding.ivDate.visibility = View.VISIBLE
                        binding.tvDate.visibility = View.VISIBLE
                        loadRecyclerInfoYear()
                    }
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
            when(dateSelected){
                "Todo" -> loadRecyclerInfoAll()
                "Año" -> loadRecyclerInfoYear()
                "Mes" -> loadRecyclerInfoMonth()
                "Día" -> loadRecyclerInfoDay()
            }
        }


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
            dateSelected = "Todo"
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


    /*Date Pickers
    * ---------------------------------
    * */
    fun select_datePick() {
        when (dateSelected) {
            "Todo" -> li_dateYear()
            "Día" -> li_dateDay()
            "Mes" -> li_dateMonth()
            "Año" -> li_dateYear()
        }
    }

    fun li_dateYear(){
        val inflater = LayoutInflater.from(binding.root.context)
        val liBinding = LiDateYBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(liBinding.root)
        val alertDialog = builder.create()

        //Declare
        liBinding.ilNpAnno.maxValue = 2050
        liBinding.ilNpAnno.minValue = 2020

        //Listeners
        liBinding.btnCancel.setOnClickListener{
            alertDialog.dismiss()
        }
        liBinding.btnAcept.setOnClickListener{
            alertDialog.dismiss()
            year = liBinding.ilNpAnno.value
            binding.tvDate.text = year.toString()
            loadRecyclerInfoYear()
        }

        //Finish
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

    }

    fun li_dateMonth(){

        val inflater = LayoutInflater.from(binding.root.context)
        val liBinding = LiDateYmBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(liBinding.root)
        val alertDialog = builder.create()

        //Declare
        liBinding.ilNpAnnos.maxValue = 2050
        liBinding.ilNpAnnos.minValue = 2020
        liBinding.ilNpMonth.maxValue = 11
        liBinding.ilNpMonth.minValue = 0
        val months = arrayOf("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre")
        liBinding.ilNpMonth.displayedValues = months

        //Listeners
        liBinding.btnCancel.setOnClickListener{
            alertDialog.dismiss()
        }
        liBinding.btnAcept.setOnClickListener{
            alertDialog.dismiss()
            year = liBinding.ilNpAnnos.value
            month = liBinding.ilNpMonth.value+1
            val dateDisplay="${year}/${month}"
            binding.tvDate.text = dateDisplay
            loadRecyclerInfoMonth()
        }

        //Finish
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

    }

    fun li_dateDay(){

        val inflater = LayoutInflater.from(binding.root.context)
        val liBinding = LiDateYmdBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(liBinding.root)
        val alertDialog = builder.create()

        //Declare
        liBinding.ilNpAnnos.maxValue = 2050
        liBinding.ilNpAnnos.minValue = 2020
        liBinding.ilNpMonth.maxValue = 11
        liBinding.ilNpMonth.minValue = 0
        liBinding.ilNpDay.minValue = 1
        liBinding.ilNpDay.maxValue = 31
        val months = arrayOf("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre")
        liBinding.ilNpMonth.displayedValues = months

        //Listeners
        liBinding.btnCancel.setOnClickListener{
            alertDialog.dismiss()
        }
        liBinding.btnAcept.setOnClickListener{
            alertDialog.dismiss()
            year = liBinding.ilNpAnnos.value
            month = liBinding.ilNpMonth.value + 1
            day = liBinding.ilNpDay.value
            val dateDisplay="${year}/${month}/${day}"
            binding.tvDate.text = dateDisplay
            loadRecyclerInfoDay()
        }

        //Finish
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

    }


}