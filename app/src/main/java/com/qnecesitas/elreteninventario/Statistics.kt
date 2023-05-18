package com.qnecesitas.elreteninventario

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.auxiliary.NetworkTools
import com.qnecesitas.elreteninventario.data.ModelSale
import com.qnecesitas.elreteninventario.databinding.ActivityStatisticsBinding
import com.qnecesitas.elreteninventario.databinding.LiDateYBinding
import com.qnecesitas.elreteninventario.databinding.LiDateYmBinding
import com.qnecesitas.elreteninventario.databinding.LiDateYmdBinding
import com.qnecesitas.elreteninventario.network.RetrofitSalesImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class Statistics : AppCompatActivity() {

    lateinit var binding: ActivityStatisticsBinding

    //Internet
    private lateinit var retrofitSalesImpl: RetrofitSalesImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }


        //retrofit
        retrofitSalesImpl = RetrofitSalesImpl()


        //profits Day
        binding.ivProfitDayCalendar.setOnClickListener {
            liDateDay(this::callProfitDay)
        }

        //profits Month
        binding.ivProfitMonthCalendar.setOnClickListener {
            liDateMonth(this::callProfitMonth)
        }

        //profits Year
        binding.ivProfitYearCalendar.setOnClickListener {
            liDateYear(this::callProfitYear)
        }

        //sales Day
        binding.ivSalesDayCalendar.setOnClickListener {
            liDateDay(this::callSalesDay)
        }

        //sales Month
        binding.ivSalesMonthCalendar.setOnClickListener {
            liDateMonth(this::callSalesMonth)
        }

        //sales Year
        binding.ivSalesYearCalendar.setOnClickListener {
            liDateYear(this::callSalesYear)
        }

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val sales = getSalesFromMonths(year)
        updateChart(prepareDataChart(sales))
    }

    //Profits Day
    private fun callProfitDay(year: Int, month: Int, day: Int) {
        if (NetworkTools.isOnline(binding.root.context, false)) {
            binding.refresh.isRefreshing = true
            val call = retrofitSalesImpl.fetchOrdersD(Constants.PHP_TOKEN, year, month, day)
            call.enqueue(object : Callback<ArrayList<ModelSale>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelSale>>,
                    response: Response<java.util.ArrayList<ModelSale>>
                ) {
                    binding.refresh.isRefreshing = false
                    if (response.isSuccessful) {
                        binding.tvProfitDayResponse.text = composeProfit(response.body()!!)
                        binding.tvProfitDayResponse.visibility = View.VISIBLE
                    } else {
                        showAlertDialogNoInternetProfitDay(this@Statistics, year, month, day)
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelSale>>,
                    t: Throwable
                ) {
                    showAlertDialogNoInternetProfitDay(this@Statistics, year, month, day)
                    binding.refresh.isRefreshing = false
                }
            })
        } else {
            showAlertDialogNoInternetProfitDay(this@Statistics, year, month, day)
        }
    }

































































































    fun showAlertDialogNoInternetProfitDay(context: Context, year: Int, month: Int, day: Int) {
        //init alert dialog
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setTitle(context.getString(R.string.Se_ha_producido_un_error))
        builder.setMessage(R.string.Revise_su_conexion)
        //set listeners for dialog buttons
        builder.setPositiveButton(
            R.string.Reintentar
        ) { dialog, _ ->
            dialog.dismiss()
            callProfitDay(year, month, day)
        }
        //create the alert dialog and show it
        builder.create().show()
    }

    //Profits Month
    private fun callProfitMonth(year: Int, month: Int) {
        if (NetworkTools.isOnline(binding.root.context, false)) {
            binding.refresh.isRefreshing = true
            val call = retrofitSalesImpl.fetchOrdersM(Constants.PHP_TOKEN, year, month)
            call.enqueue(object : Callback<ArrayList<ModelSale>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelSale>>,
                    response: Response<java.util.ArrayList<ModelSale>>
                ) {
                    binding.refresh.isRefreshing = false
                    if (response.isSuccessful) {
                        binding.tvProfitMonthResponse.text = composeProfit(response.body()!!)
                        binding.tvProfitMonthResponse.visibility = View.VISIBLE
                    } else {
                        showAlertDialogNoInternetProfitMonth(this@Statistics, year, month)
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelSale>>,
                    t: Throwable
                ) {
                    showAlertDialogNoInternetProfitMonth(this@Statistics, year, month)
                    binding.refresh.isRefreshing = false
                }
            })
        } else {
            showAlertDialogNoInternetProfitMonth(this@Statistics, year, month)
        }
    }

    fun showAlertDialogNoInternetProfitMonth(context: Context, year: Int, month: Int) {
        //init alert dialog
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setTitle(context.getString(R.string.Se_ha_producido_un_error))
        builder.setMessage(R.string.Revise_su_conexion)
        //set listeners for dialog buttons
        builder.setPositiveButton(
            R.string.Reintentar
        ) { dialog, _ ->
            dialog.dismiss()
            callProfitMonth(year, month)
        }
        //create the alert dialog and show it
        builder.create().show()
    }

    //Profits Year
    private fun callProfitYear(year: Int) {
        if (NetworkTools.isOnline(binding.root.context, false)) {
            binding.refresh.isRefreshing = true
            val call = retrofitSalesImpl.fetchOrdersY(Constants.PHP_TOKEN, year)
            call.enqueue(object : Callback<ArrayList<ModelSale>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelSale>>,
                    response: Response<java.util.ArrayList<ModelSale>>
                ) {
                    binding.refresh.isRefreshing = false
                    if (response.isSuccessful) {
                        binding.tvProfitYearResponse.text = composeProfit(response.body()!!)
                        binding.tvProfitYearResponse.visibility = View.VISIBLE
                    } else {
                        showAlertDialogNoInternetProfitYear(this@Statistics, year)
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelSale>>,
                    t: Throwable
                ) {
                    showAlertDialogNoInternetProfitYear(this@Statistics, year)
                    binding.refresh.isRefreshing = false
                }
            })
        } else {
            showAlertDialogNoInternetProfitYear(this@Statistics, year)
        }
    }

    fun showAlertDialogNoInternetProfitYear(context: Context, year: Int) {
        //init alert dialog
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setTitle(context.getString(R.string.Se_ha_producido_un_error))
        builder.setMessage(R.string.Revise_su_conexion)
        //set listeners for dialog buttons
        builder.setPositiveButton(
            R.string.Reintentar
        ) { dialog, _ ->
            dialog.dismiss()
            callProfitYear(year)
        }
        //create the alert dialog and show it
        builder.create().show()
    }

    fun composeProfit(list: ArrayList<ModelSale>): String {
        var totalPrice = 0.0
        var totalInv = 0.0
        list.forEach {
            totalPrice += it.totalPrice
            totalInv += it.totalInv
        }
        return (totalPrice - totalInv).toString()
    }

    //Sales Day
    private fun callSalesDay(year: Int, month: Int, day: Int) {
        if (NetworkTools.isOnline(binding.root.context, false)) {
            binding.refresh.isRefreshing = true
            val call = retrofitSalesImpl.fetchOrdersD(Constants.PHP_TOKEN, year, month, day)
            call.enqueue(object : Callback<ArrayList<ModelSale>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelSale>>,
                    response: Response<java.util.ArrayList<ModelSale>>
                ) {
                    binding.refresh.isRefreshing = false
                    if (response.isSuccessful) {
                        binding.tvSalesDayResponse.text = response.body()!!.size.toString()
                        binding.tvSalesDayResponse.visibility = View.VISIBLE
                    } else {
                        showAlertDialogNoInternetSalesDay(this@Statistics, year, month, day)
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelSale>>,
                    t: Throwable
                ) {
                    showAlertDialogNoInternetSalesDay(this@Statistics, year, month, day)
                    binding.refresh.isRefreshing = false
                }
            })
        } else {
            showAlertDialogNoInternetSalesDay(this@Statistics, year, month, day)
        }
    }

    fun showAlertDialogNoInternetSalesDay(context: Context, year: Int, month: Int, day: Int) {
        //init alert dialog
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setTitle(context.getString(R.string.Se_ha_producido_un_error))
        builder.setMessage(R.string.Revise_su_conexion)
        //set listeners for dialog buttons
        builder.setPositiveButton(
            R.string.Reintentar
        ) { dialog, _ ->
            dialog.dismiss()
            callSalesDay(year, month, day)
        }
        //create the alert dialog and show it
        builder.create().show()
    }

    //Sales Month
    private fun callSalesMonth(year: Int, month: Int) {
        if (NetworkTools.isOnline(binding.root.context, false)) {
            binding.refresh.isRefreshing = true
            val call = retrofitSalesImpl.fetchOrdersM(Constants.PHP_TOKEN, year, month)
            call.enqueue(object : Callback<ArrayList<ModelSale>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelSale>>,
                    response: Response<java.util.ArrayList<ModelSale>>
                ) {
                    binding.refresh.isRefreshing = false
                    if (response.isSuccessful) {
                        binding.tvSalesMonthResponse.text = response.body()!!.size.toString()
                        binding.tvSalesMonthResponse.visibility = View.VISIBLE
                    } else {
                        showAlertDialogNoInternetSalesMonth(this@Statistics, year, month)
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelSale>>,
                    t: Throwable
                ) {
                    showAlertDialogNoInternetSalesMonth(this@Statistics, year, month)
                    binding.refresh.isRefreshing = false
                }
            })
        } else {
            showAlertDialogNoInternetSalesMonth(this@Statistics, year, month)
        }
    }

    fun showAlertDialogNoInternetSalesMonth(context: Context, year: Int, month: Int) {
        //init alert dialog
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setTitle(context.getString(R.string.Se_ha_producido_un_error))
        builder.setMessage(R.string.Revise_su_conexion)
        //set listeners for dialog buttons
        builder.setPositiveButton(
            R.string.Reintentar
        ) { dialog, _ ->
            dialog.dismiss()
            callSalesMonth(year, month)
        }
        //create the alert dialog and show it
        builder.create().show()
    }

    //Sales Year
    private fun callSalesYear(year: Int) {
        if (NetworkTools.isOnline(binding.root.context, false)) {
            binding.refresh.isRefreshing = true
            val call = retrofitSalesImpl.fetchOrdersY(Constants.PHP_TOKEN, year)
            call.enqueue(object : Callback<ArrayList<ModelSale>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelSale>>,
                    response: Response<java.util.ArrayList<ModelSale>>
                ) {
                    binding.refresh.isRefreshing = false
                    if (response.isSuccessful) {
                        binding.tvSalesYearResponse.text = response.body()!!.size.toString()
                        binding.tvSalesYearResponse.visibility = View.VISIBLE
                    } else {
                        showAlertDialogNoInternetSalesYear(this@Statistics, year)
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelSale>>,
                    t: Throwable
                ) {
                    showAlertDialogNoInternetSalesYear(this@Statistics, year)
                    binding.refresh.isRefreshing = false
                }
            })
        } else {
            showAlertDialogNoInternetSalesYear(this@Statistics, year)
        }
    }

    fun showAlertDialogNoInternetSalesYear(context: Context, year: Int) {
        //init alert dialog
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setTitle(context.getString(R.string.Se_ha_producido_un_error))
        builder.setMessage(R.string.Revise_su_conexion)
        //set listeners for dialog buttons
        builder.setPositiveButton(
            R.string.Reintentar
        ) { dialog, _ ->
            dialog.dismiss()
            callSalesYear(year)
        }
        //create the alert dialog and show it
        builder.create().show()
    }


    private fun liDateYear(actionSet: (Int) -> Unit) {
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
            val year = liBinding.ilNpAnno.value
            actionSet(year)
        }

        //Finish
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

    }

    private fun liDateMonth(actionSet: (Int, Int) -> Unit) {

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
            val year = liBinding.ilNpAnnos.value
            val month = liBinding.ilNpMonth.value + 1
            actionSet(year, month)
        }

        //Finish
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

    }

    private fun liDateDay(actionSet: (Int, Int, Int) -> Unit) {

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
            val year = liBinding.ilNpAnnos.value
            val month = liBinding.ilNpMonth.value + 1
            val day = liBinding.ilNpDay.value
            actionSet(year, month, day)
        }

        //Finish
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

   //update chart
    private fun getSalesFromMonths(year: Int): ArrayList<ModelSale> {
        var list = ArrayList<ModelSale>()
        if (NetworkTools.isOnline(binding.root.context, false)) {
            binding.refresh.isRefreshing = true
            val call = retrofitSalesImpl.fetchOrdersY(Constants.PHP_TOKEN, year)
            call.enqueue(object : Callback<ArrayList<ModelSale>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelSale>>,
                    response: Response<java.util.ArrayList<ModelSale>>
                ) {
                    binding.refresh.isRefreshing = false
                    if (response.isSuccessful) {
                        list = response.body()!!
                    } else {
                        showAlertDialogNoInternetProfitYear(this@Statistics, year)
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelSale>>,
                    t: Throwable
                ) {
                    showAlertDialogNoInternetProfitYear(this@Statistics, year)
                    binding.refresh.isRefreshing = false
                }
            })
        } else {
            showAlertDialogNoInternetProfitYear(this@Statistics, year)
        }
        return list
    }
    private fun prepareDataChart(list : ArrayList<ModelSale>) : List<Entry> {
        val listOfEntries = mutableListOf<Entry>()
        val salesByMonth = ArrayList<ModelSale>()
        for(x in 1 .. 12){
            for(model in list){
                if (model.month == x){
                    salesByMonth.add(model)
                }
            }
            listOfEntries.add(Entry(x.toFloat(), composeProfit(salesByMonth).toFloat()))
        }
        return listOfEntries
    }
    private fun updateChart(entries: List<Entry>) {
        Toast.makeText(this,""+entries.size,Toast.LENGTH_SHORT).show()
        //create LineDataSet
        val dataSet = LineDataSet(entries, "Meses")
        dataSet.color = R.color.orange
        dataSet.valueTextColor = Color.RED
        dataSet.axisDependency = YAxis.AxisDependency.LEFT //axisY only in left side
        //create LineData will show the chart
        val lineData = LineData(dataSet)
        binding.chart.data = lineData
        //create tags for axisX trying to don't show intermediate values
        val labels = arrayListOf("", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
        //set the formatter with the tags
        val formatter = IndexAxisValueFormatter(labels)
        binding.chart.xAxis.valueFormatter = formatter
        //minor setting
        binding.chart.description.text = "Ganancias en este año" //description
        binding.chart.setTouchEnabled(true) //user can't interact with chart
        binding.chart.animateY(1000) //inner animation
        binding.chart.xAxis.position = XAxis.XAxisPosition.BOTTOM //axisX in bottom
    }
}