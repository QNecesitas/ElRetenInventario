package com.qnecesitas.elreteninventario

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.qnecesitas.elreteninventario.adapters.AdapterR_DeficitProduct
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelEditProductLS
import com.qnecesitas.elreteninventario.data.ModelEditProductS
import com.qnecesitas.elreteninventario.database.Repository
import com.qnecesitas.elreteninventario.databinding.ActivityDeficitBinding
import com.qnecesitas.elreteninventario.databinding.LiAlterAmountBinding
import com.qnecesitas.elreteninventario.network.RetrofitProductsImplS
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Activity_Deficit : AppCompatActivity() {

    //Binding
    private lateinit var binding: ActivityDeficitBinding
    private lateinit var li_alter_amount_binding: LiAlterAmountBinding

    //Array
    private lateinit var al_deficitProduct: MutableList<ModelEditProductS>
    private lateinit var adapterR_DeficitProduct: AdapterR_DeficitProduct

    //Internet
    private lateinit var repository: Repository

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
        lifecycleScope.launch {
            al_deficitProduct = repository.fetchProductsDeficit(selectButton)
            binding.adRecycler.visibility = View.VISIBLE
            binding.adNotInfo.visibility = View.GONE
            updateRecyclerAdapter()
        }
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
        adapterR_DeficitProduct.setRecyclerClickListener(object : AdapterR_DeficitProduct.RecyclerClickListener{
            override fun onClick(modelS: ModelEditProductS) {
                li_amount(modelS)
            }

        })
        binding.adRecycler.adapter = adapterR_DeficitProduct

    }

    private fun li_amount(modelEditProductS: ModelEditProductS) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_alter_amount_binding = LiAlterAmountBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_alter_amount_binding.root)
        val alertDialog = builder.create()

        //Filling and listeners
        var currentAmount: Int = modelEditProductS.amount
        li_alter_amount_binding.et.setText(currentAmount.toString())

        li_alter_amount_binding.ivBtnMore.setOnClickListener(View.OnClickListener {
            if (currentAmount != 99999) {
                currentAmount++
                li_alter_amount_binding.et.setText(currentAmount.toString())
            }
        })

        li_alter_amount_binding.ivBtnLess.setOnClickListener(View.OnClickListener {
            if (currentAmount != 1) {
                currentAmount--
                li_alter_amount_binding.et.setText(currentAmount.toString())
            }
        })

        li_alter_amount_binding.et.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (li_alter_amount_binding.et.getText().toString() == "0") {
                    currentAmount = 1
                    li_alter_amount_binding.et.setText(currentAmount.toString())
                } else if (li_alter_amount_binding.et.getText().toString() == "") {
                    currentAmount = 1
                } else {
                    currentAmount = li_alter_amount_binding.et.getText().toString().toInt()
                }
            }

            override fun afterTextChanged(editable: Editable) {}
            override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
            ) {
            }
        })

        li_alter_amount_binding.btnAccept.setOnClickListener {
            alertDialog.dismiss()
            if (li_alter_amount_binding.et.text.toString().trim().isNotEmpty()) {
                if (selectButton != "Almacén") {
                    uploadAmountChangesInternetLS(
                            li_alter_amount_binding.et.text.toString().toInt(),
                            ModelEditProductLS(
                                    modelEditProductS.c_productS,
                                    modelEditProductS.name,
                                    modelEditProductS.fk_c_sessionS,
                                    modelEditProductS.amount,
                                    modelEditProductS.buyPrice,
                                    modelEditProductS.salePrice,
                                    modelEditProductS.descr,
                                    modelEditProductS.statePhoto,
                                    modelEditProductS.deficit,
                                    modelEditProductS.size,
                                    modelEditProductS.brand
                            )
                    )
                } else {
                    uploadAmountChangesInternet(
                            li_alter_amount_binding.et.text.toString().toInt(),
                            modelEditProductS
                    )
                }
            } else {
                li_alter_amount_binding.et.error = getString(R.string.este_campo_no_debe_vacio)
            }
        }

        li_alter_amount_binding.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        //Finished
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun uploadAmountChangesInternet(amount: Int, modelEditProductS: ModelEditProductS) {
        lifecycleScope.launch {


            repository.alterAmountS(
                    modelEditProductS.c_productS,
                    amount
            )
        }



        FancyToast.makeText(
                this,
                getString(R.string.Operacion_realizada_con_exito),
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
        ).show()
        modelEditProductS.amount = amount
        updateRecyclerAdapter()

    }

    private fun uploadAmountChangesInternetLS(amount: Int, modelEditProductLS: ModelEditProductLS) {
        lifecycleScope.launch {


            repository.alterAmountLS(
                    modelEditProductLS.c_productLS,
                    amount
            )
        }



        FancyToast.makeText(
                this,
                getString(R.string.Operacion_realizada_con_exito),
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
        ).show()
        modelEditProductLS.amount = amount
        updateRecyclerAdapter()

    }


}