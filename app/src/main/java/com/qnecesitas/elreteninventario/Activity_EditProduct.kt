package com.qnecesitas.elreteninventario

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qnecesitas.elreteninventario.adapters.AdapterR_EditProduct
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.auxiliary.FragmentsInfo
import com.qnecesitas.elreteninventario.auxiliary.ImageTools
import com.qnecesitas.elreteninventario.auxiliary.NetworkTools
import com.qnecesitas.elreteninventario.auxiliary.Permissions
import com.qnecesitas.elreteninventario.data.ModelEditProduct
import com.qnecesitas.elreteninventario.data.ModelProductPath
import com.qnecesitas.elreteninventario.databinding.ActivityEditProductBinding
import com.qnecesitas.elreteninventario.databinding.LiAddProductBinding
import com.qnecesitas.elreteninventario.databinding.LiAlterAmountBinding
import com.qnecesitas.elreteninventario.databinding.LiInfoProductBinding
import com.qnecesitas.elreteninventario.network.RetrofitProductsImplS
import com.shashank.sony.fancytoastlib.FancyToast
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Activity_EditProduct : AppCompatActivity() {

    private lateinit var binding: ActivityEditProductBinding

    //Add Session
    private var li_add_binding: LiAddProductBinding? = null

    //Info Product
    private var li_info_binding: LiInfoProductBinding? = null

    //Edit amount
    private lateinit var li_alter_amount_binding : LiAlterAmountBinding

    //Edit Product
    private lateinit var al_editProduct: ArrayList<ModelEditProduct>
    private lateinit var adapterR_editProducts: AdapterR_EditProduct

    //Internet
    private lateinit var retrofitProductsImplS: RetrofitProductsImplS
    private val PERMISO_GALERIA = 3
    private var imageFile = "no"
    private var uriLLegadaRecortada: Uri? = null

    //Results launchers
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    //CL Transfer
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toolbar
        setSupportActionBar(binding.aepToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.aepToolbar.setNavigationOnClickListener { finish() }

        //Init
        retrofitProductsImplS = RetrofitProductsImplS()
        //Button Add
        if (FragmentsInfo.LAST_CODE_SESSION_SENDED == "no") {
            binding.aepFabAdd.visibility = View.GONE
        } else binding.aepFabAdd.setOnClickListener { click_add() }


        //Results launchers
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                imageReceived(result)
            }


        //Refresh
        binding.aepRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                loadRecyclerInfo()
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
            loadRecyclerInfo()
        }
        loadRecyclerInfo()
    }


    /**Initial thread**/
    private fun loadRecyclerInfo() {
        if (NetworkTools.isOnline(binding.root.context, false)) {
            binding.aepRefresh.isRefreshing = true

            val call =
                if (FragmentsInfo.LAST_CODE_SESSION_SENDED.equals("no")) retrofitProductsImplS.fetchProductsSAll(
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
                    binding.aepRefresh.isRefreshing = false
                    if (response.isSuccessful) {
                        alertNotInternet(false)
                        al_editProduct = response.body()!!
                        updateRecyclerAdapter()
                    } else {
                        alertNotInternet(true)
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelEditProduct>>,
                    t: Throwable
                ) {
                    alertNotInternet(true)
                    binding.aepRefresh.isRefreshing = false
                }
            })
        } else {
            alertNotInternet(true)
        }
    }

    private fun updateRecyclerAdapter() {
        if (al_editProduct.isEmpty()) {
            alertNotElements(true)
        } else {
            alertNotElements(false)
        }
        adapterR_editProducts = AdapterR_EditProduct(al_editProduct, binding.root.context)

        adapterR_editProducts.setRecyclerOnClickListener(object :
            AdapterR_EditProduct.RecyclerClickListener {
            override fun onClick(position: Int) {
                li_infoProduct(position)
            }
        })
        binding.aepRecycler.adapter = adapterR_editProducts
    }


    /** Alerts in the background**/
    private fun alertNotElements(open: Boolean) {
        if (open) {
            binding.aepNotInfo.visibility = View.VISIBLE
            binding.aepRecycler.visibility = View.GONE
            binding.aepNotConnection.visibility = View.GONE
        } else {
            binding.aepNotInfo.visibility = View.GONE
            binding.aepRecycler.visibility = View.VISIBLE
            binding.aepNotConnection.visibility = View.GONE
        }
    }

    private fun alertNotInternet(open: Boolean) {
        if (open) {
            binding.aepNotConnection.visibility = View.VISIBLE
            binding.aepRecycler.visibility = View.GONE
            binding.aepNotInfo.visibility = View.GONE
        } else {
            binding.aepNotConnection.visibility = View.GONE
            binding.aepRecycler.visibility = View.VISIBLE
            binding.aepNotInfo.visibility = View.GONE
        }
    }

    private fun showAlertDialogDeleteProducts(position: Int) {
        //init alert dialog
        val builder = android.app.AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(R.string.Eliminar_elemento)
        builder.setMessage(R.string.Desea_eliminar_la_gaveta)
        //set listeners for dialog buttons
        builder.setPositiveButton(R.string.Si) { dialog, _ ->
            dialog.dismiss()
            deleteProductInternet(position)
        }
        builder.setNegativeButton(R.string.No) { dialog, _ ->
            dialog.dismiss()
        }

        //create the alert dialog and show it
        builder.create().show()
    }

    private fun showAlertDialogPath(path: String) {
        //init alert dialog
        val builder = android.app.AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(R.string.Ubicacion)
        builder.setMessage(path)
        //set listeners for dialog buttons
        builder.setPositiveButton(R.string.Aceptar) { dialog, _ ->
            dialog.dismiss()
        }

        //create the alert dialog and show it
        builder.create().show()
    }


    /** Inflated layouts Edit, Add and Info**/
    private fun li_infoProduct(position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_info_binding = LiInfoProductBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_info_binding?.root)
        val alertDialog = builder.create()

        //Init
        val name = getString(R.string.Nombre_Info, al_editProduct[position].name)
        val code = getString(R.string.Codigo_Info, al_editProduct[position].c_productS)
        val amount = getString(R.string.Cantidad_Info, al_editProduct[position].amount)
        val buyPrice = getString(R.string.PrecioC_Info, al_editProduct[position].buyPrice)
        val salePrice = getString(R.string.PrecioV_Info, al_editProduct[position].salePrice)
        val descr = getString(R.string.Descripcion_Info, al_editProduct[position].descr)
        val codeImage = al_editProduct[position].c_productS

        //Fill out
        li_info_binding?.infoName?.text = name
        li_info_binding?.infoCode?.text = code
        li_info_binding?.infoAmount?.text = amount
        li_info_binding?.infoPriceB?.text = buyPrice
        li_info_binding?.infoPriceS?.text = salePrice
        li_info_binding?.infoDescr?.text = descr
        li_info_binding?.image?.let {
            Glide.with(applicationContext)
                .load(Constants.PHP_IMAGES + "P_" + codeImage + ".jpg")
                .error(R.drawable.widgets)
                .centerCrop()
                .into(it)
        }

        //Listeners
        li_info_binding?.close?.setOnClickListener { alertDialog.dismiss() }
        li_info_binding?.settings?.setOnClickListener {
            val popupMenu = PopupMenu(applicationContext, li_info_binding?.settings)
            popupMenu.menuInflater.inflate(R.menu.menu_product_options, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                alertDialog.dismiss()
                when (menuItem.itemId) {
                    R.id.option_cantidad -> li_amount(position)
                    R.id.option_editar -> li_editProduct(position)
                    R.id.option_transferir -> showClTransfer(position)
                    R.id.option_eliminar -> showAlertDialogDeleteProducts(position)
                    R.id.option_ubicacion -> findLocationClick(position)
                }
                false
            }
            popupMenu.show()
        }

        //Finalizado
        builder.setCancelable(true)
        alertDialog.window?.setGravity(Gravity.CENTER)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun li_editProduct(position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_add_binding = LiAddProductBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_add_binding?.root)
        val alertDialog = builder.create()

        //Init
        imageFile = "no"
        val name = al_editProduct[position].name
        val code = al_editProduct[position].c_productS
        val amount = al_editProduct[position].amount
        val buyPrice = al_editProduct[position].buyPrice
        val salePrice = al_editProduct[position].salePrice
        val descr = al_editProduct[position].descr
        val codeImage = al_editProduct[position].c_productS

        //Fill out
        li_add_binding?.tietName?.setText(name)
        li_add_binding?.tietCode?.setText(code)
        li_add_binding?.tietCant?.setText(amount.toString())
        li_add_binding?.tietPriceBuy?.setText(buyPrice.toString())
        li_add_binding?.tietPriceSale?.setText(salePrice.toString())
        li_add_binding?.tietDesc?.setText(descr)
        li_add_binding?.image?.let {
            Glide.with(applicationContext)
                .load(Constants.PHP_IMAGES + "P_" + codeImage + ".jpg")
                .error(R.drawable.widgets)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(it)
        }


        //Listener
        li_add_binding?.image?.setOnClickListener {
            val popupMenu = PopupMenu(applicationContext, li_add_binding?.image)
            popupMenu.menuInflater.inflate(R.menu.menu_image_add, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.menu_image_add) {
                    escogerimagenGaleria()
                } else if (menuItem.itemId == R.id.menu_image_del) {
                    li_add_binding?.image?.setImageDrawable(null)
                    imageFile = "delete"
                }
                false
            }
            popupMenu.show()
        }
        li_add_binding?.btnCancel?.setOnClickListener(View.OnClickListener {
            alertDialog.dismiss()
            imageFile = "no"
        })
        li_add_binding?.btnAccept?.setOnClickListener(View.OnClickListener {
            if (checkInfoDataAdd()) {
                alertDialog.dismiss()
                updateProductInternet(
                    al_editProduct[position].c_productS,
                    li_add_binding?.tietCode?.text.toString(),
                    li_add_binding?.tietName?.text.toString(),
                    li_add_binding?.tietCant?.text.toString().toInt(),
                    li_add_binding?.tietPriceBuy?.text.toString().toDouble(),
                    li_add_binding?.tietPriceSale?.text.toString().toDouble(),
                    li_add_binding?.tietDesc?.text.toString(),
                    if (li_add_binding?.image == null) 0 else 1,
                    position
                )
            }
        })

        //Finalizado
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun li_addProduct() {
        val inflater = LayoutInflater.from(binding.root.context)
        li_add_binding = LiAddProductBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_add_binding?.root)
        val alertDialog = builder.create()

        //Variables
        imageFile = "no"
        var c_Product: String
        var n_Product: String
        var amount: Int
        var buyPrice: Double
        var salePrice: Double
        var descr: String
        var statePhoto: Int

        //Listener
        li_add_binding?.image?.setOnClickListener {
            val popupMenu = PopupMenu(applicationContext, li_add_binding?.image)
            popupMenu.menuInflater.inflate(R.menu.menu_image_add, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.menu_image_add) {
                    escogerimagenGaleria()
                } else if (menuItem.itemId == R.id.menu_image_del) {
                    li_add_binding?.image?.setImageDrawable(null)
                    imageFile = "no"
                }
                false
            }
            popupMenu.show()
        }
        li_add_binding?.btnCancel?.setOnClickListener(View.OnClickListener { alertDialog.dismiss() })
        li_add_binding?.btnAccept?.setOnClickListener(View.OnClickListener {
            if (checkInfoDataAdd()) {
                //Declaraciones
                c_Product = li_add_binding?.tietCode?.text.toString()
                n_Product = li_add_binding?.tietName?.text.toString()
                amount = li_add_binding?.tietCant?.text.toString().toInt()
                buyPrice = li_add_binding?.tietPriceBuy?.text.toString().toDouble()
                salePrice = li_add_binding?.tietPriceSale?.text.toString().toDouble()
                descr = li_add_binding?.tietDesc?.text.toString()
                statePhoto = if (li_add_binding?.image == null) 0 else 1

                alertDialog.dismiss()
                addProductInternet(
                    c_Product,
                    n_Product,
                    amount,
                    buyPrice,
                    salePrice,
                    descr,
                    statePhoto
                )
            }
        })

        //Finalizado
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun li_amount(position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_alter_amount_binding = LiAlterAmountBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_alter_amount_binding.root)
        val alertDialog = builder.create()

        //Filling and listeners
        var currentAmount : Int = al_editProduct.get(position).amount
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
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        })

        li_alter_amount_binding.btnAccept.setOnClickListener{
            alertDialog.dismiss()
            uploadAmountChangesInternet(currentAmount, position)
        }

        li_alter_amount_binding.btnCancel.setOnClickListener{
            alertDialog.dismiss()
        }

        //Finalizado
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun findLocationClick(position: Int){
        fetchProductsPathInternet(position)
    }

    /**Activity Events**/
    private fun click_add() {
        li_addProduct()
    }


    /**Upload info to Internet**/
    private fun addProductInternet(
        c_Product: String,
        n_Product: String,
        amount: Int,
        buyPrice: Double,
        salePrice: Double,
        descr: String,
        statePhoto: Int
    ) {
        if (NetworkTools.isOnline(binding.root.context, true)) {
            binding.aepRefresh.isRefreshing = true
            val call = retrofitProductsImplS.addProduct(
                Constants.PHP_TOKEN,
                c_Product,
                n_Product,
                FragmentsInfo.LAST_CODE_SESSION_SENDED,
                amount,
                buyPrice,
                salePrice,
                descr,
                imageFile
            )
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    binding.aepRefresh.isRefreshing = false
                    if (response.isSuccessful) {
                        val model = ModelEditProduct(
                            c_Product,
                            n_Product,
                            FragmentsInfo.LAST_CODE_SESSION_SENDED,
                            amount,
                            buyPrice,
                            salePrice,
                            descr,
                            statePhoto
                        )
                        al_editProduct.add(model)
                        updateRecyclerAdapter()
                        FancyToast.makeText(
                            applicationContext,
                            getString(R.string.Operacion_realizada_con_exito),
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false
                        ).show()
                    } else {
                        FancyToast.makeText(
                            applicationContext,
                            getString(R.string.Revise_su_conexion),
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<String>,
                    t: Throwable
                ) {
                    FancyToast.makeText(
                        applicationContext,
                        getString(R.string.Revise_su_conexion),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false
                    ).show()
                    binding.aepRefresh.isRefreshing = false
                }

            })
        } else {
            FancyToast.makeText(
                applicationContext,
                getString(R.string.Revise_su_conexion),
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,
                false
            ).show()
        }
    }

    private fun updateProductInternet(
        c_ProductOld: String,
        c_Product: String,
        name: String,
        amount: Int,
        buyPrice: Double,
        salePrice: Double,
        descr: String,
        statePhoto: Int,
        position: Int
    ) {
        if (NetworkTools.isOnline(binding.root.context, true)) {
            binding.aepRefresh.isRefreshing = true
            val call = retrofitProductsImplS.updateProduct(
                Constants.PHP_TOKEN,
                c_ProductOld,
                imageFile,
                c_Product,
                name,
                FragmentsInfo.LAST_CODE_SESSION_SENDED,
                amount,
                buyPrice,
                salePrice,
                descr,
            )
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    binding.aepRefresh.isRefreshing = false
                    if (response.isSuccessful) {
                        val model = ModelEditProduct(
                            c_Product,
                            name,
                            FragmentsInfo.LAST_CODE_SESSION_SENDED,
                            amount,
                            buyPrice,
                            salePrice,
                            descr,
                            statePhoto
                        )
                        al_editProduct[position] = model
                        updateRecyclerAdapter()
                        FancyToast.makeText(
                            applicationContext,
                            getString(R.string.Operacion_realizada_con_exito),
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false
                        ).show()
                    } else {
                        FancyToast.makeText(
                            applicationContext,
                            getString(R.string.Revise_su_conexion),
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<String>,
                    t: Throwable
                ) {
                    FancyToast.makeText(
                        applicationContext,
                        getString(R.string.Revise_su_conexion),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false
                    ).show()
                    binding.aepRefresh.isRefreshing = false
                }

            })
        } else {
            FancyToast.makeText(
                applicationContext,
                getString(R.string.Revise_su_conexion),
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,
                false
            ).show()
        }
    }

    private fun deleteProductInternet(position: Int) {
        if (NetworkTools.isOnline(this, false)) {
            binding.aepRefresh.isRefreshing = true

            val call = retrofitProductsImplS.deleteProduct(
                Constants.PHP_TOKEN,
                al_editProduct[position].c_productS,
                al_editProduct[position].fk_c_sessionS
            )


            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    binding.aepRefresh.isRefreshing = false
                    if (response.isSuccessful) {
                        FancyToast.makeText(
                            applicationContext,
                            getString(R.string.Operacion_realizada_con_exito),
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false
                        ).show()
                        al_editProduct.removeAt(position)
                        updateRecyclerAdapter()
                    } else {
                        FancyToast.makeText(
                            applicationContext,
                            getString(R.string.Revise_su_conexion),
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false
                        ).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    binding.aepRefresh.isRefreshing = false
                    FancyToast.makeText(
                        applicationContext,
                        getString(R.string.Revise_su_conexion),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false
                    ).show()
                }
            })


        } else {
            FancyToast.makeText(
                applicationContext,
                getString(R.string.Revise_su_conexion),
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,
                false
            ).show()
        }
    }

    private fun transferProductInternet(position: Int, codeSession: String) {
        if (NetworkTools.isOnline(this, false)) {
            binding.aepRefresh.isRefreshing = true
            val call = retrofitProductsImplS.transferProducts(
                Constants.PHP_TOKEN,
                al_editProduct[position].c_productS,
                al_editProduct[position].name,
                al_editProduct[position].fk_c_sessionS,
                al_editProduct[position].amount,
                al_editProduct[position].buyPrice,
                al_editProduct[position].salePrice,
                al_editProduct[position].descr,
                al_editProduct[position].statePhoto,
                codeSession
            )
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    binding.aepRefresh.isRefreshing = false
                    if (response.isSuccessful) {
                        FancyToast.makeText(
                            applicationContext,
                            getString(R.string.Operacion_realizada_con_exito),
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false
                        ).show()
                        al_editProduct.removeAt(position)
                        updateRecyclerAdapter()
                    } else {
                        FancyToast.makeText(
                            applicationContext,
                            getString(R.string.Revise_su_conexion),
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<String>,
                    t: Throwable
                ) {
                    FancyToast.makeText(
                        applicationContext,
                        getString(R.string.Revise_su_conexion),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false
                    ).show()
                    binding.aepRefresh.isRefreshing = false
                }

            })

        } else {
            FancyToast.makeText(
                applicationContext,
                getString(R.string.Revise_su_conexion),
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,
                false
            ).show()
        }
    }

    private fun uploadAmountChangesInternet(amount: Int, position: Int){
        if (NetworkTools.isOnline(this, false)) {
            binding.aepRefresh.isRefreshing = true

            val call = retrofitProductsImplS.alterAmountS(
                Constants.PHP_TOKEN,
                al_editProduct[position].c_productS,
                amount
            )


            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    binding.aepRefresh.isRefreshing = false
                    if (response.isSuccessful) {
                        FancyToast.makeText(
                            applicationContext,
                            getString(R.string.Operacion_realizada_con_exito),
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false
                        ).show()
                        al_editProduct[position].amount = amount
                        updateRecyclerAdapter()
                    } else {
                        FancyToast.makeText(
                            applicationContext,
                            getString(R.string.Revise_su_conexion),
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false
                        ).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    binding.aepRefresh.isRefreshing = false
                    FancyToast.makeText(
                        applicationContext,
                        getString(R.string.Revise_su_conexion),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false
                    ).show()
                }
            })


        } else {
            FancyToast.makeText(
                applicationContext,
                getString(R.string.Revise_su_conexion),
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,
                false
            ).show()
        }
    }

    private fun fetchProductsPathInternet(position: Int){
        val call = retrofitProductsImplS.fetchProductSPath(
            Constants.PHP_TOKEN,
            al_editProduct[position].c_productS
        )

        call.enqueue(object : Callback<ArrayList<ModelProductPath>> {
            override fun onResponse(call: Call<ArrayList<ModelProductPath>>, response: Response<ArrayList<ModelProductPath>>) {
                binding.aepRefresh.isRefreshing = false
                if (response.isSuccessful) {
                    FancyToast.makeText(
                        applicationContext,
                        getString(R.string.Operacion_realizada_con_exito),
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                    val al_modelPath = response.body()
                    val path =
                        al_modelPath?.get(0)?.fk_c_shelfS + "/"+
                                al_modelPath?.get(0)?.fk_c_drawerS +"/"+
                                al_editProduct[position].fk_c_sessionS
                    showAlertDialogPath(path)
                    updateRecyclerAdapter()
                } else {
                    FancyToast.makeText(
                        applicationContext,
                        getString(R.string.Revise_su_conexion),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false
                    ).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<ModelProductPath>>, t: Throwable) {
                binding.aepRefresh.isRefreshing = false
                FancyToast.makeText(
                    applicationContext,
                    getString(R.string.Revise_su_conexion),
                    FancyToast.LENGTH_LONG,
                    FancyToast.ERROR,
                    false
                ).show()
            }
        })


    }


    /**Auxiliary Methods**/
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_CANCELED) {
            return
        }


        //UCrop
        if (requestCode == UCrop.REQUEST_CROP) {


            if (data != null) {
                imageRecorted(data)
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.error_obtener_imagen),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        if (requestCode == UCrop.RESULT_ERROR) {
            FancyToast.makeText(
                applicationContext,
                getString(R.string.error_obtener_imagen),
                FancyToast.LENGTH_SHORT,
                FancyToast.ERROR,
                false
            ).show()
        }
    }

    private fun checkInfoDataAdd(): Boolean {
        var amountTrue = 0

        //Name
        if (li_add_binding?.tietName?.text!!.isNotEmpty()) {
            amountTrue++
        } else {
            li_add_binding?.tilName?.error = getString(R.string.este_campo_no_debe_vacio)
        }

        //Code
        if (li_add_binding?.tietCode?.text!!.isNotEmpty()) {
            amountTrue++
        } else {
            li_add_binding?.tilCode?.error = getString(R.string.este_campo_no_debe_vacio)
        }

        //PriceBuy
        if (li_add_binding?.tietPriceBuy?.text!!.isNotEmpty()) {
            amountTrue++
        } else {
            li_add_binding?.tilPriceBuy?.error = getString(R.string.este_campo_no_debe_vacio)
        }

        //PriceSale
        if (li_add_binding?.tietPriceSale?.text!!.isNotEmpty()) {
            amountTrue++
        } else {
            li_add_binding?.tilPriceSale?.error = getString(R.string.este_campo_no_debe_vacio)
        }

        //Amount
        if (li_add_binding?.tietCant?.text?.isNotEmpty() == true) {
            amountTrue++
        } else {
            li_add_binding?.tilCant?.error = getString(R.string.este_campo_no_debe_vacio)
        }

        //Desc
        if (li_add_binding?.tietDesc?.text?.isNotEmpty() == true) {
            amountTrue++
        } else {
            li_add_binding?.tilDesc?.error = getString(R.string.este_campo_no_debe_vacio)
        }

        return amountTrue == 6
    }

    private fun escogerimagenGaleria() {
        if (Permissions.siHayPermisoDeAlmacenamiento(applicationContext)) {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher.launch(galleryIntent)
        } else {
            Permissions.pedirPermisoDeAlmacenamiento(this@Activity_EditProduct, PERMISO_GALERIA)
        }
    }

    private fun imageReceived(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val contentUri = data?.data
            val file = ImageTools.createTempImageFile(
                applicationContext,
                ImageTools.getHoraActual("yyMMddHHmmss")
            )
            if (contentUri != null) {
                recortarImagen(contentUri, Uri.fromFile(file))
            } else {
                Toast.makeText(
                    applicationContext,
                    R.string.error_obtener_imagen,
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.error_obtener_imagen,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun recortarImagen(uri1: Uri, uri2: Uri) {
        try {
            UCrop.of(uri1, uri2)
                .withAspectRatio(3f, 3f)
                .withMaxResultSize(
                    ImageTools.ANCHO_DE_FOTO_A_SUBIR,
                    ImageTools.ALTO_DE_FOTO_A_SUBIR
                )
                .start(this)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
    }

    private fun imageRecorted(data: Intent?) {
        if (data != null) {
            uriLLegadaRecortada = UCrop.getOutput(data)
            li_add_binding?.image?.setImageURI(uriLLegadaRecortada)
            val bitmap = (li_add_binding?.image?.drawable as BitmapDrawable).bitmap
            imageFile = ImageTools.convertImageString(bitmap).toString()
        }
    }

    /**Transfer System**/
    private fun showClTransfer(position: Int) {
        binding.aepClTransfer.visibility = View.VISIBLE
        //Toolbar
        setSupportActionBar(binding.clTransferToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.clTransferToolbar.setNavigationOnClickListener { onBack(position) }

        //Fragments
        fragmentManager = supportFragmentManager
        showFragmentShelvesLS(position)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBack(position)
            }
        })
    }

    private fun showFragmentShelvesLS(position: Int) {
        binding.clTransferToolbar.setTitle(R.string.Seleccione_Estante)
        FragmentsInfo.LAST_FRAGMENT_TOUCHED = FragmentsInfo.Companion.EFragments.FR_SHELVES
        val fragment_shelvesLS = Fragment_ShelvesLS()
        fragment_shelvesLS.setOpenShelfLSListener(object : Fragment_ShelvesLS.OpenShelfLS {
            override fun onShelfLSClicked(c_shelfLS: String) {
                showFragmentDrawersLS(c_shelfLS, position)
            }
        })
        fragmentManager.beginTransaction()
            .replace(binding.clTransferFrame.id, fragment_shelvesLS)
            .commit()
    }

    private fun showFragmentDrawersLS(c_shelfS: String, position: Int) {
        binding.clTransferToolbar.setTitle(R.string.Seleccione_Gaveta)
        FragmentsInfo.LAST_CODE_SHELVES_LS_SENDED = c_shelfS
        FragmentsInfo.LAST_FRAGMENT_TOUCHED = FragmentsInfo.Companion.EFragments.FR_DRAWERS
        val fragment_drawersLS = Fragment_DrawerLS(c_shelfS)
        fragment_drawersLS.setOpenDrawerLSListener(object : Fragment_DrawerLS.OpenDrawerLS {
            override fun onDrawerLSClicked(code: String) {
                showFragmentSessionsLS(code, position)
            }
        })
        fragmentManager.beginTransaction()
            .replace(binding.clTransferFrame.id, fragment_drawersLS)
            .commit()
    }

    private fun showFragmentSessionsLS(c_drawerS: String, position: Int) {
        binding.clTransferToolbar.setTitle(R.string.Secciones)
        FragmentsInfo.LAST_CODE_DRAWER_LS_SENDED = c_drawerS
        FragmentsInfo.LAST_FRAGMENT_TOUCHED = FragmentsInfo.Companion.EFragments.FR_SESSION
        val fragment_sessionsLS = Fragment_SessionsLS(c_drawerS)
        fragment_sessionsLS.setOpenSessionLSListener(object : Fragment_SessionsLS.OpenSessionLS {
            override fun onSessionLSClicked(c_sessionLS: String) {
                binding.aepClTransfer.visibility = View.GONE
                transferProductInternet(position, c_sessionLS)
            }
        })
        fragmentManager.beginTransaction()
            .replace(binding.clTransferFrame.id, fragment_sessionsLS)
            .commit()
    }

    private fun onBack(position: Int) {
        when (FragmentsInfo.LAST_FRAGMENT_TOUCHED) {
            FragmentsInfo.Companion.EFragments.FR_SHELVES -> finish()
            FragmentsInfo.Companion.EFragments.FR_DRAWERS -> showFragmentShelvesLS(position)
            FragmentsInfo.Companion.EFragments.FR_SESSION -> showFragmentDrawersLS(
                FragmentsInfo.LAST_CODE_SHELVES_SENDED,
                position
            )

            FragmentsInfo.Companion.EFragments.AC_PRODUCTS -> showFragmentSessionsLS(
                FragmentsInfo.LAST_CODE_DRAWER_SENDED,
                position
            )
        }
    }

}