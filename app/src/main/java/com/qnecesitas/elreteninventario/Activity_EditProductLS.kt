package com.qnecesitas.elreteninventario

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
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
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qnecesitas.elreteninventario.adapters.AdapterR_EditProductLS
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.auxiliary.FragmentsInfo
import com.qnecesitas.elreteninventario.auxiliary.IDCreater
import com.qnecesitas.elreteninventario.auxiliary.ImageTools
import com.qnecesitas.elreteninventario.data.ModelEditProductLS
import com.qnecesitas.elreteninventario.data.ModelProductPath
import com.qnecesitas.elreteninventario.data.ModelProductPathLS
import com.qnecesitas.elreteninventario.database.Repository
import com.qnecesitas.elreteninventario.databinding.ActivityEditProductLsBinding
import com.qnecesitas.elreteninventario.databinding.LiAddProductBinding
import com.qnecesitas.elreteninventario.databinding.LiAlterAmountBinding
import com.qnecesitas.elreteninventario.databinding.LiInfoProductBinding
import com.shashank.sony.fancytoastlib.FancyToast
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch
import java.io.File
import java.net.URI

class Activity_EditProductLS : AppCompatActivity() {

    private lateinit var binding: ActivityEditProductLsBinding

    //Add Session
    private var li_add_binding: LiAddProductBinding? = null

    //Info Product
    private var li_info_binding: LiInfoProductBinding? = null

    //Edit amount
    private lateinit var li_alter_amount_binding: LiAlterAmountBinding

    //Edit Product
    private lateinit var al_editProduct: MutableList<ModelEditProductLS>
    private lateinit var al_editProductSAll: MutableList<ModelEditProductLS>
    private lateinit var adapterR_editProducts: AdapterR_EditProductLS
    private var isContracted = false

    //Database
    private lateinit var repository: Repository
    private var uriImageCut: Uri? = null
    private var lastTranferAmount = 0
    private var lastTransferExist = 0
    private var lastTransferAllFill = 0
    private var lastFilterStr = ""
    private var lastPositionRecycler = 0

    //Edit photo
    private var processPhotoInCourse = false;

    //Results launchers
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    //CL Transfer
    lateinit var fragmentManager: FragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductLsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toolbar
        setSupportActionBar(binding.aepToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.aepToolbar.setNavigationOnClickListener { finish() }

        //Init
        repository = Repository(application as ElRetenApplication)
        //Button Add

        if (FragmentsInfo.STORE_ACCESS == FragmentsInfo.Companion.EAccess.Admin) {
            binding.aepFabAdd.visibility = View.VISIBLE
            binding.aepFabAdd.setOnClickListener { click_add() }
        }


        //Results launchers
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                imageReceived(result)
            }



        //RecyclerView
        binding.aepRecycler.setHasFixedSize(true)
        al_editProduct = ArrayList()
        al_editProductSAll = ArrayList()
        binding.aepIvContract.setOnClickListener {
            isContracted = if (isContracted) {
                binding.aepIvContract.setImageDrawable(
                    AppCompatResources.getDrawable(
                        this ,
                        R.drawable.baseline_format_list_bulleted_24
                    )
                )
                false
            } else {
                binding.aepIvContract.setImageDrawable(
                    AppCompatResources.getDrawable(this , R.drawable.baseline_grid_on_24)
                )
                true
            }
            updateRecyclerAdapter()
        }
        adapterR_editProducts =
            AdapterR_EditProductLS(al_editProduct , applicationContext , isContracted , false)

        //Search
        binding.aepSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                adapterR_editProducts.getFilter()?.filter(s)
                lastFilterStr = s
                return true
            }
        })

        loadRecyclerInfo()
    }




    /*Fetch products
    *--------------
     */
    private fun loadRecyclerInfo() {
        lifecycleScope.launch {
            al_editProduct =
                    if (FragmentsInfo.LAST_CODE_SESSION_LS_SENDED == "no") repository.fetchProductsLSAll()
                    else repository.fetchProductsLS(
                            FragmentsInfo.LAST_CODE_SESSION_LS_SENDED
                    )
            loadRecyclerAllS()
        }

    }

    private fun loadRecyclerAllS() {
        lifecycleScope.launch {
            al_editProductSAll = repository.fetchProductsLSAll()
            updateRecyclerAdapter()
        }

    }

    private fun updateRecyclerAdapter() {
        if (al_editProduct.isNotEmpty()) {
            al_editProduct.sortBy { it.name }
        }
        if (al_editProductSAll.isNotEmpty()) {
            al_editProductSAll.sortBy { it.name }
        }

        val layoutManager = binding.aepRecycler.layoutManager as LinearLayoutManager
        lastPositionRecycler = layoutManager.findFirstVisibleItemPosition()

        if (al_editProduct.isEmpty()) {
            alertNotElements(true)
        } else {
            alertNotElements(false)
        }


        adapterR_editProducts =
                AdapterR_EditProductLS(al_editProduct , binding.root.context , isContracted , false)

        adapterR_editProducts.setRecyclerOnClickListener(object :
                AdapterR_EditProductLS.RecyclerClickListener {
            override fun onClick(position: Int) {
                li_infoProduct(position)
            }
        })
        binding.aepRecycler.adapter = adapterR_editProducts
        if (isContracted) {
            binding.aepRecycler.layoutManager = LinearLayoutManager(
                    this ,
                    LinearLayoutManager.VERTICAL , false
            )
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
            if (screenWidthDp >= 600) {
                binding.aepRecycler.layoutManager = GridLayoutManager(this , 7)
            } else {
                binding.aepRecycler.layoutManager = GridLayoutManager(this , 2)
            }
        }

        if (lastFilterStr.trim().isNotEmpty()) {
            adapterR_editProducts.getFilter()?.filter(lastFilterStr)
        }

        val layoutManager1 = binding.aepRecycler.layoutManager as LinearLayoutManager
        layoutManager1.scrollToPosition(lastPositionRecycler)
    }




    /*Add products
   *--------------
    */
    private fun liAddProduct() {
        val inflater = LayoutInflater.from(binding.root.context)
        li_add_binding = LiAddProductBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_add_binding?.root)
        val alertDialog = builder.create()

        //Variables
        uriImageCut = null
        var cProduct = IDCreater.generate()
        var name: String
        var amount: Int
        var buyPrice: Double
        var salePrice: Double
        var descr: String
        var statePhoto: Int
        var deficit: Int
        var size: String
        var brand: String

        li_add_binding?.tietCode?.setText(cProduct)

        //Listener
        li_add_binding?.image?.setOnClickListener {
            val popupMenu = PopupMenu(applicationContext , li_add_binding?.image)
            popupMenu.menuInflater.inflate(R.menu.menu_image_add , popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.menu_image_add) {
                    choiceGalleryImage()
                } else if (menuItem.itemId == R.id.menu_image_del) {
                    li_add_binding?.image?.setImageDrawable(null)
                    uriImageCut = null
                }
                false
            }
            popupMenu.show()
        }
        li_add_binding?.btnCancel?.setOnClickListener { alertDialog.dismiss() }
        li_add_binding?.btnAccept?.setOnClickListener {
            if (checkInfoDataAdd()) {
                //Declarations
                cProduct = li_add_binding?.tietCode?.text.toString()
                name = li_add_binding?.tietName?.text.toString()
                amount = li_add_binding?.tietCant?.text.toString().toInt()
                buyPrice = li_add_binding?.tietPriceBuy?.text.toString().toDouble()
                salePrice = li_add_binding?.tietPriceSale?.text.toString().toDouble()
                descr = li_add_binding?.tietDesc?.text.toString()
                statePhoto = if (uriImageCut == null) 0 else 1
                deficit = li_add_binding?.tietDeficit?.text.toString().toInt()
                size = li_add_binding?.tietSize?.text.toString()
                brand = li_add_binding?.tietBrand?.text.toString()

                alertDialog.dismiss()
                lifecycleScope.launch {
                    if (!repository.isDuplicatedS(cProduct)) {
                        addProductDB(
                                ModelEditProductLS(
                                        cProduct, name, FragmentsInfo.LAST_CODE_SESSION_LS_SENDED, amount, buyPrice, salePrice, descr, statePhoto, deficit, size, brand
                                )
                        )
                    }else{
                        showAlertDialogDuplicated()
                    }
                }
            }
        }

        //Finish
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun addProductDB(productLS: ModelEditProductLS) {
        lifecycleScope.launch {
            repository.addProductLS(
                    productLS.c_productLS,
                    productLS.name,
                    FragmentsInfo.LAST_CODE_SESSION_LS_SENDED,
                    productLS.amount,
                    productLS.buyPrice,
                    productLS.salePrice,
                    productLS.descr,
                    productLS.deficit,
                    productLS.size,
                    productLS.brand
            )
        }

        al_editProduct.add(productLS)
        updateRecyclerAdapter()
        if(productLS.statePhoto == 1) {
            val bitmap = li_add_binding?.image?.drawable?.toBitmap()
            bitmap?.let { ImageTools.saveImageToInternalStorage(this, it, productLS.c_productLS) }
        }
        FancyToast.makeText(
                applicationContext ,
                getString(R.string.Operacion_realizada_con_exito) ,
                FancyToast.LENGTH_LONG ,
                FancyToast.SUCCESS ,
                false
        ).show()
    }




    /*
    *----------Choice Image
     */
    private fun choiceGalleryImage() {
        val galleryIntent =
                Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }

    private fun imageReceived(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val contentUri = data?.data
            val file = ImageTools.createTempImageFile(
                    this@Activity_EditProductLS ,
                    ImageTools.getHoraActual("yyMMddHHmmss")
            )
            if (contentUri != null) {
                cutImage(contentUri , Uri.fromFile(file))
            } else {
                Toast.makeText(
                        this@Activity_EditProductLS ,
                        R.string.error_obtener_imagen ,
                        Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                    this@Activity_EditProductLS ,
                    R.string.error_obtener_imagen ,
                    Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun cutImage(uri1: Uri, uri2: Uri) {
        try {
            UCrop.of(uri1 , uri2)
                    .withAspectRatio(3f , 3f)
                    .withMaxResultSize(
                            ImageTools.ANCHO_DE_FOTO_A_SUBIR ,
                            ImageTools.ALTO_DE_FOTO_A_SUBIR
                    )
                    .start(this)
        } catch (e: Exception) {
            Toast.makeText(
                    this@Activity_EditProductLS ,
                    getString(R.string.error) ,
                    Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun imageCropped(data: Intent?) {
        if (data != null) {
            this.uriImageCut = UCrop.getOutput(data)
            li_add_binding?.image?.setImageURI(this.uriImageCut)
        }
    }




    /*
    *--------------Edit Product
     */
    private fun liEditProduct(position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_add_binding = LiAddProductBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_add_binding?.root)
        val alertDialog = builder.create()

        //Init
        val name = al_editProduct[position].name
        val code = al_editProduct[position].c_productLS
        val amount = al_editProduct[position].amount
        val buyPrice = al_editProduct[position].buyPrice
        val salePrice = al_editProduct[position].salePrice
        val descr = al_editProduct[position].descr
        val codeImage = al_editProduct[position].c_productLS
        val deficit = al_editProduct[position].deficit
        val size = al_editProduct[position].size
        val brand = al_editProduct[position].brand
        var statePhoto = al_editProduct[position].statePhoto
        var uriToDelete: Uri? = null
        uriImageCut = null

        //Fill out
        if(statePhoto==1) {
            val cw = ContextWrapper(this)
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            uriImageCut = File(directory, "${code}.jpg").toUri()
            uriToDelete = File(directory, "${code}.jpg").toUri()
        }
        li_add_binding?.tietName?.setText(name)
        li_add_binding?.tietCode?.setText(code)
        li_add_binding?.tietCant?.setText(amount.toString())
        li_add_binding?.tietPriceBuy?.setText(buyPrice.toString())
        li_add_binding?.tietPriceSale?.setText(salePrice.toString())
        li_add_binding?.tietDesc?.setText(descr)
        li_add_binding?.tietSize?.setText(size)
        li_add_binding?.tietBrand?.setText(brand)
        li_add_binding?.image?.let {
            if(statePhoto == 1) {
                val cw = ContextWrapper(this)
                val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
                it.setImageURI(File(directory, "${codeImage}.jpg").toUri())
            }
            if(processPhotoInCourse){
                processPhotoInCourse = false
                it.setImageURI(uriImageCut)
            }
        }
        li_add_binding?.tietCode?.isEnabled = false
        li_add_binding?.tietDeficit?.setText(deficit.toString())


        //Listener
        li_add_binding?.image?.setOnClickListener {
            val popupMenu = PopupMenu(applicationContext , li_add_binding?.image)
            popupMenu.menuInflater.inflate(R.menu.menu_image_add , popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.menu_image_add) {
                    choiceGalleryImage()
                } else if (menuItem.itemId == R.id.menu_image_del) {
                    li_add_binding?.image?.setImageDrawable(null)
                    uriImageCut = null
                }
                false
            }
            popupMenu.show()
        }
        li_add_binding?.btnCancel?.setOnClickListener(View.OnClickListener {
            alertDialog.dismiss()
            uriImageCut = null
        })
        li_add_binding?.btnAccept?.setOnClickListener(View.OnClickListener {
            if (checkInfoDataAdd()) {
                alertDialog.dismiss()
                statePhoto = if(uriImageCut == null) 0 else 1
                if(uriImageCut == null) File(URI(uriToDelete.toString())).delete()
                updateProductDB(
                        ModelEditProductLS(
                                al_editProduct[position].c_productLS,
                                al_editProduct[position].fk_c_sessionLS,
                                li_add_binding?.tietName?.text.toString(),
                                li_add_binding?.tietCant?.text.toString().toInt(),
                                li_add_binding?.tietPriceBuy?.text.toString().toDouble(),
                                li_add_binding?.tietPriceSale?.text.toString().toDouble(),
                                li_add_binding?.tietDesc?.text.toString(),
                                statePhoto,
                                li_add_binding?.tietDeficit?.text.toString().toInt(),
                                li_add_binding?.tietSize?.text.toString(),
                                li_add_binding?.tietBrand?.text.toString()
                        ), position
                )
            }
        })

        //Finished
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun updateProductDB(productLS: ModelEditProductLS, position: Int) {
        lifecycleScope.launch {
            repository.updateProductLS(
                    productLS.c_productLS ,
                    productLS.name ,
                    productLS.amount ,
                    productLS.buyPrice ,
                    productLS.salePrice ,
                    productLS.descr ,
                    productLS.deficit ,
                    productLS.size ,
                    productLS.brand,
                    productLS.statePhoto
            )
        }

        al_editProduct[position] = productLS
        if(productLS.statePhoto == 1) {
            val bitmap = li_add_binding?.image?.drawable?.toBitmap()
            bitmap?.let { ImageTools.saveImageToInternalStorage(this, it, productLS.c_productLS) }
        }
        updateRecyclerAdapter()
        FancyToast.makeText(
                this,
                getString(R.string.Operacion_realizada_con_exito) ,
                FancyToast.LENGTH_LONG ,
                FancyToast.SUCCESS ,
                false
        ).show()
    }



    /*
    *--------------Delete Product
     */
    private fun deleteProductDB(position: Int) {
        lifecycleScope.launch {
            repository.deleteProductLS(
                    al_editProduct[position].c_productLS ,
                    al_editProduct[position].fk_c_sessionLS
            )
            val cw = ContextWrapper(this@Activity_EditProductLS)
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            File(directory, "${al_editProduct[position].c_productLS}.jpg").delete()
            al_editProduct.removeAt(position)
            updateRecyclerAdapter()
        }
        FancyToast.makeText(
                this@Activity_EditProductLS ,
                getString(R.string.Operacion_realizada_con_exito) ,
                FancyToast.LENGTH_LONG ,
                FancyToast.SUCCESS ,
                false
        ).show()
    }




    /*
    *----------Alerts
     */
    private fun alertNotElements(open: Boolean) {
        if (open) {
            binding.aepNotInfo.visibility = View.VISIBLE
            binding.aepRecycler.visibility = View.GONE
        } else {
            binding.aepNotInfo.visibility = View.GONE
            binding.aepRecycler.visibility = View.VISIBLE
        }
    }

    private fun showAlertDialogDeleteProducts(position: Int) {
        //init alert dialog
        val builder = android.app.AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(R.string.Eliminar_elemento)
        builder.setMessage(R.string.Desea_eliminar_la_gaveta)
        //set listeners for dialog buttons
        builder.setPositiveButton(R.string.Si) { dialog , _ ->
            dialog.dismiss()
            deleteProductDB(position)
        }
        builder.setNegativeButton(R.string.No) { dialog , _ ->
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
        builder.setPositiveButton(R.string.Aceptar) { dialog , _ ->
            dialog.dismiss()
        }

        //create the alert dialog and show it
        builder.create().show()
    }

    private fun showAlertDialogDuplicated() {
        //init alert dialog
        val builder = android.app.AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(R.string.Elemento_repetido)
        builder.setMessage(R.string.Elemento_repetido_desc)
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
        val name = getString(R.string.Producto_Info , al_editProduct[position].name)
        val code = getString(R.string.Codigo_Info , al_editProduct[position].c_productLS)
        val amount = getString(R.string.Cantidad_Info , al_editProduct[position].amount)
        val buyPrice = getString(R.string.PrecioC_Info , al_editProduct[position].buyPrice)
        val salePrice = getString(R.string.PrecioV_Info , al_editProduct[position].salePrice)
        val descr = getString(R.string.Descripcion_Info , al_editProduct[position].descr)
        val size = getString(R.string.Size_Info , al_editProduct[position].size)
        val brand = getString(R.string.Brand_Info , al_editProduct[position].brand)
        val codeImage = al_editProduct[position].c_productLS

        //Fill out
        li_info_binding?.infoName?.text = name
        li_info_binding?.infoCode?.text = code
        li_info_binding?.infoAmount?.text = amount
        li_info_binding?.infoPriceB?.text = buyPrice
        li_info_binding?.infoPriceS?.text = salePrice
        li_info_binding?.infoDescr?.text = descr
        li_info_binding?.infoSize?.text = size
        li_info_binding?.infoBrand?.text = brand
        li_info_binding?.image?.let {
            val cw = ContextWrapper(this)
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            Glide.with(this)
                    .load(File(directory, "${al_editProduct[position].c_productLS}.jpg"))
                    .error(R.drawable.widgets)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(it)
        }

        li_info_binding?.infoPriceB?.visibility = View.GONE

        //Listeners
        li_info_binding?.close?.setOnClickListener { alertDialog.dismiss() }
        li_info_binding?.settings?.setOnClickListener {
            val popupMenu = PopupMenu(applicationContext , li_info_binding?.settings)
            popupMenu.menuInflater.inflate(R.menu.menu_product_options , popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                alertDialog.dismiss()
                when (menuItem.itemId) {
                    R.id.option_cantidad -> {
                        if (FragmentsInfo.STORE_ACCESS == FragmentsInfo.Companion.EAccess.Admin) {
                            li_amount(position)
                        } else {
                            FancyToast.makeText(
                                applicationContext ,
                                getString(R.string.No_acceso) ,
                                FancyToast.LENGTH_LONG ,
                                FancyToast.WARNING ,
                                false
                            ).show()
                        }
                    }

                    R.id.option_editar -> {
                        if (FragmentsInfo.STORE_ACCESS == FragmentsInfo.Companion.EAccess.Admin) {
                            liEditProduct(position)
                        } else {
                            FancyToast.makeText(
                                applicationContext ,
                                getString(R.string.No_acceso) ,
                                FancyToast.LENGTH_LONG ,
                                FancyToast.WARNING ,
                                false
                            ).show()
                        }
                    }

                    R.id.option_transferir -> {
                        if (FragmentsInfo.STORE_ACCESS == FragmentsInfo.Companion.EAccess.Admin) {
                            li_amountTransf(position)
                        } else {
                            FancyToast.makeText(
                                applicationContext ,
                                getString(R.string.No_acceso) ,
                                FancyToast.LENGTH_LONG ,
                                FancyToast.WARNING ,
                                false
                            ).show()
                        }
                    }

                    R.id.option_eliminar -> {
                        if (FragmentsInfo.STORE_ACCESS == FragmentsInfo.Companion.EAccess.Admin) {
                            showAlertDialogDeleteProducts(position)
                        } else {
                            FancyToast.makeText(
                                applicationContext ,
                                getString(R.string.No_acceso) ,
                                FancyToast.LENGTH_LONG ,
                                FancyToast.WARNING ,
                                false
                            ).show()
                        }
                    }

                    R.id.option_ubicacion -> findLocationClick(position)
                }
                false
            }
            popupMenu.show()
        }

        //Finished
        builder.setCancelable(true)
        alertDialog.window?.setGravity(Gravity.CENTER)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun li_amount(position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_alter_amount_binding = LiAlterAmountBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_alter_amount_binding.root)
        val alertDialog = builder.create()

        //Filling and listeners
        var currentAmount: Int = al_editProduct.get(position).amount
        li_alter_amount_binding.et.setText(currentAmount.toString())

        li_alter_amount_binding.ivBtnMore.setOnClickListener {
            if (currentAmount != 99999) {
                currentAmount++
                li_alter_amount_binding.et.setText(currentAmount.toString())
            }
        }

        li_alter_amount_binding.ivBtnLess.setOnClickListener {
            if (currentAmount != 1) {
                currentAmount--
                li_alter_amount_binding.et.setText(currentAmount.toString())
            }
        }

        li_alter_amount_binding.et.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence , i: Int , i1: Int , i2: Int) {
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
                charSequence: CharSequence ,
                i: Int ,
                i1: Int ,
                i2: Int
            ) {
            }
        })

        li_alter_amount_binding.btnAccept.setOnClickListener {
            alertDialog.dismiss()
            if (li_alter_amount_binding.et.text.toString().trim().isNotEmpty()) {
                uploadAmountChangesInternet(currentAmount , position)
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

    private fun checkIfExistIfAllSended(position: Int) {
        lastTransferAllFill = 0
        lastTransferExist = 0

        for (it in al_editProductSAll) {
            if (it.c_productLS == al_editProduct[position].c_productLS) {
                lastTransferExist = 1
            }
        }

        if (al_editProduct[position].amount == lastTranferAmount) {
            lastTransferAllFill = 1
        }


    }

    private fun findLocationClick(position: Int) {
        fetchProductsPathInternet(position)
    }

    /**Activity Events**/
    private fun click_add() {
        liAddProduct()
    }






    private fun uploadAmountChangesInternet(amount: Int , position: Int) {
        lifecycleScope.launch {
            repository.alterAmountLS(
                al_editProduct[position].c_productLS ,
                amount
            )
        }
        FancyToast.makeText(
            this@Activity_EditProductLS ,
            getString(R.string.Operacion_realizada_con_exito) ,
            FancyToast.LENGTH_LONG ,
            FancyToast.SUCCESS ,
            false
        ).show()
        al_editProduct[position].amount = amount
        updateRecyclerAdapter()
    }

    private fun fetchProductsPathInternet(position: Int) {

        FancyToast.makeText(
            this@Activity_EditProductLS ,
            getString(R.string.Operacion_realizada_con_exito) ,
            FancyToast.LENGTH_LONG ,
            FancyToast.SUCCESS ,
            false
        ).show()


        lifecycleScope.launch {
            val alModelPath = repository.fetchProductLSPath(
                    al_editProduct[position].c_productLS
            )
            val path = makePath(alModelPath, position)
            showAlertDialogPath(path)
            updateRecyclerAdapter()
        }

    }




    /**Auxiliary Methods**/
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int , resultCode: Int , data: Intent?) {
        super.onActivityResult(requestCode , resultCode , data)
        if (resultCode == RESULT_CANCELED) {
            return
        }


        //UCrop
        if (requestCode == UCrop.REQUEST_CROP) {


            if (data != null) {
                imageCropped(data)
            } else {
                Toast.makeText(
                    this@Activity_EditProductLS ,
                    getString(R.string.error_obtener_imagen) ,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        if (requestCode == UCrop.RESULT_ERROR) {
            FancyToast.makeText(
                this@Activity_EditProductLS ,
                getString(R.string.error_obtener_imagen) ,
                FancyToast.LENGTH_SHORT ,
                FancyToast.ERROR ,
                false
            ).show()
        }
    }

    private fun checkInfoDataAdd(): Boolean {
        var amountTrue = 0

        //Name
        if (li_add_binding?.tietName?.text!!.trim().isNotEmpty()) {
            amountTrue++
        } else {
            li_add_binding?.tilName?.error = getString(R.string.este_campo_no_debe_vacio)
        }

        //Code
        if (li_add_binding?.tietCode?.text!!.trim().isNotEmpty()) {
            amountTrue++
        } else {
            li_add_binding?.tilCode?.error = getString(R.string.este_campo_no_debe_vacio)
        }

        //PriceBuy
        if (li_add_binding?.tietPriceBuy?.text!!.trim().isNotEmpty()) {
            amountTrue++
        } else {
            li_add_binding?.tilPriceBuy?.error = getString(R.string.este_campo_no_debe_vacio)
        }


        //PriceSale
        if (li_add_binding?.tietPriceSale?.text!!.trim().isNotEmpty()) {
            amountTrue++
        } else {
            li_add_binding?.tilPriceSale?.error = getString(R.string.este_campo_no_debe_vacio)
        }

        //Amount
        if (li_add_binding?.tietCant?.text?.trim()!!.isNotEmpty() == true) {
            amountTrue++
        } else {
            li_add_binding?.tilCant?.error = getString(R.string.este_campo_no_debe_vacio)
        }

        //Deficit
        if (li_add_binding?.tietDeficit?.text?.trim()!!.isNotEmpty() == true) {
            amountTrue++
        } else {
            li_add_binding?.tilDeficit?.error = getString(R.string.este_campo_no_debe_vacio)
        }

        return amountTrue == 6
    }


    private fun makePath(al_modelPath: MutableList<ModelProductPathLS> , position: Int): String {
        val shelfCode = al_modelPath[0].c_shelfLS

        val drawerCode = al_modelPath[0].c_drawerLS
        val guionDrawerPosition = drawerCode.lastIndexOf("_")
        val newDrawerCode = drawerCode.substring(guionDrawerPosition + 1)

        val sessionCode = al_editProduct[position].fk_c_sessionLS
        val guionSessionPosition = sessionCode.lastIndexOf("_")
        val newSessionCode = sessionCode.substring(guionSessionPosition + 1)




        return "$shelfCode/$newDrawerCode/$newSessionCode"

    }




    /*
    ------------Transfer
     */
    private fun li_amountTransf(position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_alter_amount_binding = LiAlterAmountBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_alter_amount_binding.root)
        val alertDialog = builder.create()

        //Filling and listeners
        var currentAmount: Int = al_editProduct[position].amount
        li_alter_amount_binding.et.setText(currentAmount.toString())

        li_alter_amount_binding.ivBtnMore.setOnClickListener {
            if (currentAmount != al_editProduct.get(position).amount) {
                currentAmount++
                li_alter_amount_binding.et.setText(currentAmount.toString())
            }
        }

        li_alter_amount_binding.ivBtnLess.setOnClickListener {
            if (currentAmount != 1) {
                currentAmount--
                li_alter_amount_binding.et.setText(currentAmount.toString())
            }
        }

        li_alter_amount_binding.et.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (li_alter_amount_binding.et.text.toString() == "0") {
                    currentAmount = 1
                    li_alter_amount_binding.et.setText(currentAmount.toString())
                } else if (li_alter_amount_binding.et.getText().toString() == "") {
                    currentAmount = 1
                } else if (li_alter_amount_binding.et.getText().toString()
                                .toInt() > al_editProduct.get(position).amount
                ) {
                    li_alter_amount_binding.et.setText(al_editProduct.get(position).amount.toString())
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
                val selectedAmount = li_alter_amount_binding.et.text.toString().toInt()
                checkIfExistIfAllSended(position, selectedAmount)
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

    private fun checkIfExistIfAllSended(position: Int, selectedAmount: Int) {
        var transferAllSend = false
        var transferExist = false

        lifecycleScope.launch {
            transferExist = repository.isDuplicatedS(al_editProduct[position].c_productLS)

            transferAllSend = al_editProduct[position].amount == selectedAmount

            showClTransfer(position, transferExist, transferAllSend, selectedAmount)

        }
    }

    private fun showClTransfer(position: Int, transferExist: Boolean, transferAllSend: Boolean, selectedAmount: Int) {
        binding.aepClTransfer.visibility = View.VISIBLE
        //Toolbar
        setSupportActionBar(binding.clTransferToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.clTransferToolbar.setNavigationOnClickListener { finish() }

        //Fragments
        fragmentManager = supportFragmentManager
        showFragmentShelvesLS(position, transferExist, transferAllSend, selectedAmount)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun showFragmentShelvesLS(position: Int, transferExist: Boolean, transferAllSend: Boolean, selectedAmount: Int) {
        binding.clTransferToolbar.setTitle(R.string.Seleccione_Estante)
        FragmentsInfo.LAST_FRAGMENT_TOUCHED = FragmentsInfo.Companion.EFragments.FR_SHELVES
        val fragment_shelvesS = Fragment_Shelves()
        fragment_shelvesS.setOpenShelfSListener(object : Fragment_Shelves.OpenShelfS {
            override fun onShelfSClicked(c_shelfS: String) {
                showFragmentDrawersLS(c_shelfS, position, transferExist, transferAllSend, selectedAmount)
            }
        })
        fragmentManager.beginTransaction()
                .replace(binding.clTransferFrame.id, fragment_shelvesS)
                .commit()
    }

    private fun showFragmentDrawersLS(c_shelfS: String, position: Int, transferExist: Boolean, transferAllSend: Boolean, selectedAmount: Int) {
        binding.clTransferToolbar.setTitle(R.string.Seleccione_Gaveta)
        FragmentsInfo.LAST_CODE_SHELVES_SENDED = c_shelfS
        FragmentsInfo.LAST_FRAGMENT_TOUCHED = FragmentsInfo.Companion.EFragments.FR_DRAWERS
        val fragment_drawers = Fragment_Drawers(c_shelfS)
        fragment_drawers.setOpenDrawerSListener(object : Fragment_Drawers.OpenDrawerS {
            override fun onDrawerSClicked(code: String) {
                showFragmentSessionsLS(code, position, transferExist, transferAllSend, selectedAmount)
            }
        })
        fragmentManager.beginTransaction()
                .replace(binding.clTransferFrame.id, fragment_drawers)
                .commit()
    }

    private fun showFragmentSessionsLS(c_drawerS: String, position: Int, transferExist: Boolean, transferAllSend: Boolean, selectedAmount: Int) {
        binding.clTransferToolbar.setTitle(R.string.Secciones)
        FragmentsInfo.LAST_CODE_DRAWER_SENDED = c_drawerS
        FragmentsInfo.LAST_FRAGMENT_TOUCHED = FragmentsInfo.Companion.EFragments.FR_SESSION
        val fragment_sessions = Fragment_Sessions(c_drawerS)
        fragment_sessions.setOpenSessionListener(object : Fragment_Sessions.OpenSession {
            override fun onSessionClicked(c_sessions: String) {
                binding.aepClTransfer.visibility = View.GONE
                transferProductBD(position, c_sessions, transferExist, transferAllSend, selectedAmount)
            }
        })
        fragmentManager.beginTransaction()
                .replace(binding.clTransferFrame.id, fragment_sessions)
                .commit()
    }

    private fun transferProductBD(position: Int, codeSession: String, transferExist: Boolean, transferAllSend: Boolean, selectedAmount: Int)   {
        lifecycleScope.launch {

            repository.transferProductLS_S(
                    al_editProduct[position].c_productLS,
                    al_editProduct[position].name,
                    al_editProduct[position].fk_c_sessionLS,
                    selectedAmount,
                    al_editProduct[position].buyPrice,
                    al_editProduct[position].salePrice,
                    al_editProduct[position].descr,
                    al_editProduct[position].statePhoto,
                    codeSession,
                    al_editProduct[position].deficit,
                    transferExist,
                    transferAllSend,
                    al_editProduct[position].size,
                    al_editProduct[position].brand
            )

            loadRecyclerInfo()
        }

        FancyToast.makeText(
                this@Activity_EditProductLS,
                getString(R.string.Operacion_realizada_con_exito),
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
        ).show()

    }



}