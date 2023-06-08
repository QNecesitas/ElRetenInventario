package com.qnecesitas.elreteninventario

import android.app.Application
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.qnecesitas.elreteninventario.adapters.AdapterR_Sales
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelSale
import com.qnecesitas.elreteninventario.database.Repository
import com.qnecesitas.elreteninventario.databinding.ActivitySalesBinding
import com.qnecesitas.elreteninventario.databinding.LiDateYBinding
import com.qnecesitas.elreteninventario.databinding.LiDateYmBinding
import com.qnecesitas.elreteninventario.databinding.LiDateYmdBinding
import com.qnecesitas.elreteninventario.network.RetrofitSalesImpl
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class Activity_Sales : AppCompatActivity() {

    //Binding
    private lateinit var binding: ActivitySalesBinding

    //Recycler
    private lateinit var adapterrSales: AdapterR_Sales
    private lateinit var al_sales: MutableList<ModelSale>

    //Internet
    private lateinit var repository: Repository
    private var lastFilterStr = ""

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
        val alSpinner = arrayListOf(
            getString(R.string.todo),
            getString(R.string.Dia),
            getString(R.string.Mes),
            getString(R.string.Anno)
        )
        val adapterSpinner =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, alSpinner);
        binding.spinner.adapter = adapterSpinner
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                long: Long
            ) {
                val value: String = parent?.getItemAtPosition(position).toString()
                dateSelected = value
                when (value) {
                    "Todo" -> {
                        binding.ivDate.visibility = View.GONE
                        binding.tvDate.visibility = View.GONE
                        loadRecyclerInfoAll()
                    }

                    "Día" -> {
                        binding.ivDate.visibility = View.VISIBLE
                        binding.tvDate.visibility = View.VISIBLE
                        loadRecyclerInfoDay()
                    }

                    "Mes" -> {
                        binding.ivDate.visibility = View.VISIBLE
                        binding.tvDate.visibility = View.VISIBLE
                        loadRecyclerInfoMonth()
                    }

                    "Año" -> {
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
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterrSales.getFilter()?.filter(newText)
                lastFilterStr = newText.toString()
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
            when (dateSelected) {
                "Todo" -> loadRecyclerInfoAll()
                "Año" -> loadRecyclerInfoYear()
                "Mes" -> loadRecyclerInfoMonth()
                "Día" -> loadRecyclerInfoDay()
            }
        }


        //Recycler
        al_sales = ArrayList()
        adapterrSales = AdapterR_Sales(al_sales, this)
        binding.rvSales.adapter = adapterrSales
        adapterrSales.setCloseClick(object : AdapterR_Sales.ITouchClose {
            override fun onClickClose(position: Int) {
                showAlertCloseSales(position)
            }
        })


        //Internet
        repository = Repository(Application())


        //Recycler
        loadRecyclerInfoAll()
    }


    /*Initial thread
    * ---------------------------------
    * */
    private fun loadRecyclerInfoAll() {

        al_sales = repository.fetchOrdersAll()

        alertNotInternet(false)
        updateRecyclerAdapter()


    }

    private fun loadRecyclerInfoYear() {

        al_sales = repository.fetchOrdersY(year)
        alertNotInternet(false)
        updateRecyclerAdapter()

    }

    private fun loadRecyclerInfoMonth() {

        al_sales = repository.fetchOrdersM(year, month)

        alertNotInternet(false)
        updateRecyclerAdapter()

    }

    private fun loadRecyclerInfoDay() {

        al_sales = repository.fetchOrdersD(year, month, day)

        alertNotInternet(false)
        updateRecyclerAdapter()

    }

    private fun updateRecyclerAdapter() {
        if (al_sales.isNotEmpty()) {
            al_sales.sortBy { it.name }
        }

        if (al_sales.isEmpty()) {
            alertNotElements(true)
        } else {
            alertNotElements(false)
        }

        adapterrSales = AdapterR_Sales(al_sales, this)
        adapterrSales.setCloseClick(object : AdapterR_Sales.ITouchClose {
            override fun onClickClose(position: Int) {
                showAlertCloseSales(position)
            }
        })


        binding.rvSales.adapter = adapterrSales

        if (lastFilterStr.trim().isNotEmpty()) {
            adapterrSales.getFilter()?.filter(lastFilterStr)
        }

    }


    /*Alerts
    * ---------------------------------
    * */
    private fun alertNotElements(open: Boolean) {
        if (open) {
            binding.notInfo.visibility = View.VISIBLE
            binding.rvSales.visibility = View.GONE
        } else {
            binding.notInfo.visibility = View.GONE
            binding.rvSales.visibility = View.VISIBLE
        }
    }

    private fun alertNotInternet(open: Boolean) {
        if (open) {
            binding.rvSales.visibility = View.GONE
            binding.notInfo.visibility = View.GONE
        } else {
            binding.rvSales.visibility = View.VISIBLE
            binding.notInfo.visibility = View.GONE
        }
    }

    /*Close
    ------------------
     */
    private fun showAlertCloseSales(position: Int) {
        //init alert dialog
        val builder = android.app.AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(R.string.Eliminar_elemento)
        builder.setMessage(R.string.Desea_eliminar_la_venta)
        //set listeners for dialog buttons
        builder.setPositiveButton(
            R.string.Si
        ) { dialog, _ ->
            dialog.dismiss()
            deleteSaleInternet(al_sales[position].c_order, position)
        }
        builder.setNegativeButton(R.string.No,
            DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })

        //create the alert dialog and show it
        builder.create().show()
    }

    private fun deleteSaleInternet(orderCode: Int, position: Int) {

        val call = repository.deleteOrder(orderCode)

        loadRecyclerInfoAll()
        FancyToast.makeText(
            this@Activity_Sales,
            getString(R.string.Operacion_realizada_con_exito),
            FancyToast.LENGTH_LONG,
            FancyToast.SUCCESS,
            false
        ).show()

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

    fun li_dateYear() {
        val inflater = LayoutInflater.from(binding.root.context)
        val liBinding = LiDateYBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(liBinding.root)
        val alertDialog = builder.create()

        //Declare
        liBinding.ilNpAnno.maxValue = 2050
        liBinding.ilNpAnno.minValue = 2020

        //Listeners
        liBinding.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        liBinding.btnAcept.setOnClickListener {
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

    fun li_dateMonth() {

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
        val months = arrayOf(
            "Enero",
            "Febrero",
            "Marzo",
            "Abril",
            "Mayo",
            "Junio",
            "Julio",
            "Agosto",
            "Septiembre",
            "Octubre",
            "Noviembre",
            "Diciembre"
        )
        liBinding.ilNpMonth.displayedValues = months

        //Listeners
        liBinding.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        liBinding.btnAcept.setOnClickListener {
            alertDialog.dismiss()
            year = liBinding.ilNpAnnos.value
            month = liBinding.ilNpMonth.value + 1
            val dateDisplay = "${year}/${month}"
            binding.tvDate.text = dateDisplay
            loadRecyclerInfoMonth()
        }

        //Finish
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

    }

    fun li_dateDay() {

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
        val months = arrayOf(
            "Enero",
            "Febrero",
            "Marzo",
            "Abril",
            "Mayo",
            "Junio",
            "Julio",
            "Agosto",
            "Septiembre",
            "Octubre",
            "Noviembre",
            "Diciembre"
        )
        liBinding.ilNpMonth.displayedValues = months

        //Listeners
        liBinding.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        liBinding.btnAcept.setOnClickListener {
            alertDialog.dismiss()
            year = liBinding.ilNpAnnos.value
            month = liBinding.ilNpMonth.value + 1
            day = liBinding.ilNpDay.value
            val dateDisplay = "${year}/${month}/${day}"
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