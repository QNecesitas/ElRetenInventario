package com.qnecesitas.elreteninventario

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelCart
import com.qnecesitas.elreteninventario.data.ModelEditProductLS
import com.qnecesitas.elreteninventario.data.ModelEditProductS
import com.qnecesitas.elreteninventario.data.ModelSale
import com.qnecesitas.elreteninventario.database.Repository
import com.qnecesitas.elreteninventario.databinding.ActivityStatisticsBinding
import com.qnecesitas.elreteninventario.databinding.LiDateYBinding
import com.qnecesitas.elreteninventario.databinding.LiDateYmBinding
import com.qnecesitas.elreteninventario.databinding.LiDateYmdBinding
import com.qnecesitas.elreteninventario.databinding.LiRankingProductsBinding
import com.qnecesitas.elreteninventario.network.RetrofitProductsImplLS
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class Statistics : AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsBinding

    private lateinit var alSalesAll: MutableList<ModelSale>

    //Ranking
    private lateinit var alRanking: MutableList<ModelCart>
    private lateinit var alAllProducts: MutableList<ModelEditProductLS>
    private lateinit var alMonthSalesRanking: MutableList<ModelSale>

    //Internet
    private lateinit var repository: Repository

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
        repository = Repository(application as ElRetenApplication)
        alSalesAll = ArrayList()
        alRanking = ArrayList()
        alMonthSalesRanking = ArrayList()

        //Date
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        val year = calendar.get(Calendar.YEAR)

        //profits Day
        binding.ivProfitDayCalendar.setOnClickListener {
            liDateDay(this::callProfitDay)
        }
        binding.tvProfitDayResponse.setOnClickListener {
            liDateDay(this::callProfitDay)
        }

        //profits Month
        binding.ivProfitMonthCalendar.setOnClickListener {
            liDateMonth(this::callProfitMonth)
        }
        binding.tvProfitMonthResponse.setOnClickListener {
            liDateMonth(this::callProfitMonth)
        }

        //profits Year
        binding.ivProfitYearCalendar.setOnClickListener {
            liDateYear(this::callProfitYear)
        }
        binding.tvProfitYearResponse.setOnClickListener {
            liDateYear(this::callProfitYear)
        }

        //sales Day
        binding.ivSalesDayCalendar.setOnClickListener {
            liDateDay(this::callSalesDay)
        }
        binding.tvSalesDayResponse.setOnClickListener {
            liDateDay(this::callSalesDay)
        }

        //sales Month
        binding.ivSalesMonthCalendar.setOnClickListener {
            liDateMonth(this::callSalesMonth)
        }
        binding.tvSalesMonthResponse.setOnClickListener {
            liDateMonth(this::callSalesMonth)
        }

        //sales Year
        binding.ivSalesYearCalendar.setOnClickListener {
            liDateYear(this::callSalesYear)
        }
        binding.tvSalesYearResponse.setOnClickListener {
            liDateYear(this::callSalesYear)
        }

        //Ranking
        binding.tvProductSell.setOnClickListener {
            calculateRanking()
        }


        binding.aepRetryConnection.setOnClickListener {
            loadSalesAllMonths(year)
        }

        loadSalesAllMonths(year)
    }


    /*Start code
    ---------------------------
     */
    private fun loadSalesAllMonths(year: Int) {
        lifecycleScope.launch {
            alSalesAll = repository.fetchOrdersY(year)
            updateChart()
        }

    }

    private fun updateChart() {
        prepareDataChart(alSalesAll)
    }

    private fun prepareDataChart(list: MutableList<ModelSale>) {
        val listOfEntries = mutableListOf<Entry>()

        for (x in 1..12) {
            val salesByMonth = ArrayList<ModelSale>()
            for (model in list) {
                if (model.month == x) {
                    salesByMonth.add(model)
                }
            }
            listOfEntries.add(Entry(x.toFloat(), composeProfit(salesByMonth).toFloat()))
        }


        drawChart(listOfEntries)
    }


    //Profits Day
    private fun callProfitDay(year: Int, month: Int, day: Int) {
        lifecycleScope.launch {
            val alFetched = repository.fetchOrdersD(year, month, day)

            binding.tvProfitDayResponse.text = composeProfit(alFetched)
            binding.tvProfitDayResponse.visibility = View.VISIBLE
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
        lifecycleScope.launch {
            val alFetched = repository.fetchOrdersM(year, month)

            binding.tvProfitMonthResponse.text = composeProfit(alFetched)
            binding.tvProfitMonthResponse.visibility = View.VISIBLE
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
        lifecycleScope.launch {
            val alFetched = repository.fetchOrdersY(year)
            binding.tvProfitYearResponse.text = composeProfit(alFetched)
            binding.tvProfitYearResponse.visibility = View.VISIBLE
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

    fun composeProfit(list: MutableList<ModelSale>): String {
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
        lifecycleScope.launch {
            val alFetched = repository.fetchOrdersD(year, month, day)
            binding.tvSalesDayResponse.text = alFetched.size.toString()
            binding.tvSalesDayResponse.visibility = View.VISIBLE
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
        lifecycleScope.launch {
            val alFetched = repository.fetchOrdersM(year, month)

            binding.tvSalesMonthResponse.text = alFetched.size.toString()
            binding.tvSalesMonthResponse.visibility = View.VISIBLE
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

        lifecycleScope.launch {
            val response = repository.fetchOrdersY(year)
            binding.tvSalesYearResponse.text = response.size.toString()
            binding.tvSalesYearResponse.visibility = View.VISIBLE
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
        val calendar = Calendar.getInstance()
        liBinding.ilNpAnno.maxValue = 2050
        liBinding.ilNpAnno.minValue = 2020
        liBinding.ilNpAnno.value = calendar.get(Calendar.YEAR)

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
        val calendar = Calendar.getInstance()
        liBinding.ilNpAnnos.maxValue = 2050
        liBinding.ilNpAnnos.minValue = 2020
        liBinding.ilNpAnnos.value = calendar.get(Calendar.YEAR)
        liBinding.ilNpMonth.maxValue = 11
        liBinding.ilNpMonth.minValue = 0
        liBinding.ilNpMonth.value = calendar.get(Calendar.MONTH)
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
        val calendar = Calendar.getInstance()
        liBinding.ilNpAnnos.maxValue = 2050
        liBinding.ilNpAnnos.minValue = 2020
        liBinding.ilNpAnnos.value = calendar.get(Calendar.YEAR)
        liBinding.ilNpMonth.maxValue = 11
        liBinding.ilNpMonth.minValue = 0
        liBinding.ilNpMonth.value = calendar.get(Calendar.MONTH)
        liBinding.ilNpDay.minValue = 1
        liBinding.ilNpDay.maxValue = 31
        liBinding.ilNpDay.value = calendar.get(Calendar.DAY_OF_MONTH)
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
    private fun drawChart(entries: List<Entry>) {

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


    /*Calculate ranking products
    -----------------
     */
    private fun calculateRanking() {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val week = calendar.get(Calendar.WEEK_OF_MONTH)
        loadRecyclerSalesMonth(year, month, week)
    }

    private fun loadRecyclerSalesMonth(year: Int, month: Int, week: Int) {
        lifecycleScope.launch {
            val response = repository.fetchOrdersM(year, month + 1)
            alMonthSalesRanking = response
            loadRecyclerAllProducts(week)
        }
    }

    private fun loadRecyclerAllProducts(week: Int) {
        lifecycleScope.launch {
            val response = repository.fetchProductsLSAll()
            alAllProducts = response
            createSalesWeekList(week)
        }
    }

    private fun createSalesWeekList(week: Int) {
        if (alMonthSalesRanking.isNotEmpty()) {
            if (alAllProducts.isNotEmpty()) {
                alRanking.clear()

                alMonthSalesRanking = alMonthSalesRanking.filter { it ->
                    val sellCalendar = Calendar.getInstance()
                    sellCalendar.firstDayOfWeek = Calendar.MONDAY
                    sellCalendar.set(Calendar.DAY_OF_MONTH, it.day)
                    sellCalendar.set(Calendar.MONTH, it.day)
                    sellCalendar.set(Calendar.YEAR, it.year)

                    val sellWeek = sellCalendar.get(Calendar.WEEK_OF_MONTH)
                    sellWeek == week
                } as ArrayList<ModelSale>

                for (modelProduct in alAllProducts) {
                    alRanking.add(
                            ModelCart(
                                    modelProduct,
                                    numberSales(modelProduct)
                            )
                    )
                }

                alRanking.sortBy { it.amount }
                alRanking = alRanking.filter { it.amount != 0 } as ArrayList<ModelCart>


                if (alRanking.size < 10) {
                    updateRecyclerRanking(alRanking)
                } else {
                    updateRecyclerRanking(
                            alRanking.subList(alRanking.size - 10, alRanking.size)
                    )
                }


            } else {
                FancyToast.makeText(
                        this,
                        getString(R.string.No_hay_productos),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false
                ).show()
            }
        } else {
            FancyToast.makeText(
                    this,
                    getString(R.string.No_hay_ventas),
                    FancyToast.LENGTH_LONG,
                    FancyToast.ERROR,
                    false
            ).show()
        }
    }

    private fun numberSales(modelEditProductS: ModelEditProductLS): Int {
        var cant = 0
        var cProdIndex: Int
        var subBeforeCode: String
        var lastAmountIndex: Int
        var subAmount: String
        var startRealAmountIndex: Int
        var endRealAmountIndex: Int
        for (modelSales in alMonthSalesRanking) {
            if (modelSales.products.contains(modelEditProductS.c_productLS)) {
                try {
                    cProdIndex = modelSales.products.indexOf(modelEditProductS.c_productLS)
                    subBeforeCode = modelSales.products.substring(0, cProdIndex)
                    lastAmountIndex = subBeforeCode.lastIndexOf("Cantidad:")
                    subAmount = subBeforeCode.substring(lastAmountIndex)
                    startRealAmountIndex = subAmount.indexOf(":") + 1
                    endRealAmountIndex = subAmount.indexOf("-")
                    cant += subAmount.substring(startRealAmountIndex, endRealAmountIndex).trim().toInt()

                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("Result", "Error")
                }
            }

        }
        return cant
    }

    private fun updateRecyclerRanking(arrayRanking: List<ModelCart>) {

        if (arrayRanking.isNotEmpty()) {

            val list = mutableListOf<String>()
            for (model in arrayRanking) {
                val cProduct = getString(R.string.Codigo_Info, model.product.c_productLS)
                val nameProduct = getString(R.string.nombre, model.product.name)
                val cantProduct = getString(R.string.ventas_cant, model.amount)
                val infoProduct = "$cProduct\n$nameProduct\n$cantProduct"
                list.add(infoProduct)
            }

            liImportantsProducts(list)
        } else {
            binding.tvProductSell.text = getString(R.string.no_hay_valores)
        }

    }

    private fun liImportantsProducts(list: List<String>) {
        val inflater = LayoutInflater.from(binding.root.context)
        val liBinding = LiRankingProductsBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(liBinding.root)
        val alertDialog = builder.create()

        //Declare
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)
        liBinding.lvListRanking.adapter = arrayAdapter
        var originalHeight = liBinding.lvListRanking.height

        //Listeners
        liBinding.ivClose.setOnClickListener(View.OnClickListener {
            alertDialog.dismiss()
        })

        //Finish
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

}