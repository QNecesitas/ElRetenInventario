package com.qnecesitas.elreteninventario

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
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
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qnecesitas.elreteninventario.adapters.AdapterR_EditProduct
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.auxiliary.FragmentsInfo
import com.qnecesitas.elreteninventario.auxiliary.IDCreater
import com.qnecesitas.elreteninventario.auxiliary.ImageTools
import com.qnecesitas.elreteninventario.data.ModelEditProductLS
import com.qnecesitas.elreteninventario.data.ModelEditProductS
import com.qnecesitas.elreteninventario.data.ModelProductPath
import com.qnecesitas.elreteninventario.data.ModelProductPathLS
import com.qnecesitas.elreteninventario.database.Repository
import com.qnecesitas.elreteninventario.databinding.ActivityEditProductBinding
import com.qnecesitas.elreteninventario.databinding.LiAddProductBinding
import com.qnecesitas.elreteninventario.databinding.LiAlterAmountBinding
import com.qnecesitas.elreteninventario.databinding.LiInfoProductBinding
import com.shashank.sony.fancytoastlib.FancyToast
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch
import java.io.File
import java.net.URI

class Activity_EditProduct : AppCompatActivity() {

    private lateinit var binding: ActivityEditProductBinding

    //Add Session
    private var li_add_binding: LiAddProductBinding? = null

    //Info Product
    private var li_info_binding: LiInfoProductBinding? = null

    //Edit amount
    private lateinit var li_alter_amount_binding: LiAlterAmountBinding

    //Recycler
    private lateinit var al_editProduct: MutableList<ModelEditProductS>
    private lateinit var adapterR_editProducts: AdapterR_EditProduct
    private var isContracted = false
    private var lastFilterStr = ""
    private var lastPositionRecycler = 0

    //Database
    private lateinit var repository: Repository
    private var uriImageCut: Uri? = null

    //Results launchers
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    //Edit product
    private var imageIsInProcess = false

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
        repository = Repository(application as ElRetenApplication)
        //Button Add
        if (FragmentsInfo.LAST_CODE_SESSION_SENDED == "no") {
            binding.aepFabAdd.visibility = View.GONE
        } else binding.aepFabAdd.setOnClickListener { click_add() }


        //Results launchers
        galleryLauncher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    imageReceived(result)
                }


        //Refresh


        //RecyclerView
        binding.aepRecycler.setHasFixedSize(true)
        al_editProduct = ArrayList()
        adapterR_editProducts =
                AdapterR_EditProduct(al_editProduct, this@Activity_EditProduct, isContracted, false)
        binding.aepIvContract.setOnClickListener {
            isContracted = if (isContracted) {
                binding.aepIvContract.setImageDrawable(
                        AppCompatResources.getDrawable(
                                this,
                                R.drawable.baseline_format_list_bulleted_24
                        )
                )
                false
            } else {
                binding.aepIvContract.setImageDrawable(
                        AppCompatResources.getDrawable(this, R.drawable.baseline_grid_on_24)
                )
                true
            }
            updateRecyclerAdapter()
        }

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


    /*
    *--------------Fetch products
     */
    private fun loadRecyclerInfo() {
        lifecycleScope.launch {
            al_editProduct = if (FragmentsInfo.LAST_CODE_SESSION_SENDED == "no") {
                repository.fetchProductsSAll()
            } else {
                repository.fetchProductsS(FragmentsInfo.LAST_CODE_SESSION_SENDED)
            }

            updateRecyclerAdapter()
        }

    }

    private fun updateRecyclerAdapter() {

        if (al_editProduct.isNotEmpty()) {
            al_editProduct.sortBy { it.name }
        }

        val layoutManager = binding.aepRecycler.layoutManager as LinearLayoutManager
        lastPositionRecycler = layoutManager.findFirstVisibleItemPosition()

        if (al_editProduct.isEmpty()) {
            alertNotElements(true)
        } else {
            alertNotElements(false)
        }

        val isAllInto = FragmentsInfo.LAST_CODE_SESSION_SENDED == "no"

        adapterR_editProducts =
                AdapterR_EditProduct(al_editProduct, binding.root.context, isContracted, isAllInto)

        adapterR_editProducts.setRecyclerOnClickListener(object :
                AdapterR_EditProduct.RecyclerClickListener {
            override fun onClick(position: Int) {
                li_infoProduct(position)
            }
        })
        binding.aepRecycler.adapter = adapterR_editProducts
        if (isContracted) {
            binding.aepRecycler.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
            if (screenWidthDp >= 800) {
                binding.aepRecycler.layoutManager = GridLayoutManager(this, 7)
            } else {
                binding.aepRecycler.layoutManager = GridLayoutManager(this, 2)
            }
        }

        if (lastFilterStr.trim().isNotEmpty()) {
            adapterR_editProducts.getFilter()?.filter(lastFilterStr)
        }

        val layoutManager1 = binding.aepRecycler.layoutManager as LinearLayoutManager
        layoutManager1.scrollToPosition(lastPositionRecycler)
    }


    /*
    *--------------Add products
     */
    private fun liAddProduct() {
        val inflater = LayoutInflater.from(binding.root.context)
        li_add_binding = LiAddProductBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_add_binding?.root)
        val alertDialog = builder.create()

        //Variables
        uriImageCut = null
        var codeProduct = IDCreater.generate()
        var name: String
        var amount: Int
        var buyPrice: Double
        var salePrice: Double
        var descr: String
        var statePhoto: Int
        var deficit: Int
        var size: String
        var brand: String

        li_add_binding?.tietCode?.setText(codeProduct)

        //Listener
        li_add_binding?.image?.setOnClickListener {
            val popupMenu = PopupMenu(this@Activity_EditProduct, li_add_binding?.image)
            popupMenu.menuInflater.inflate(R.menu.menu_image_add, popupMenu.menu)
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
                //Instances
                codeProduct = li_add_binding?.tietCode?.text.toString()
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
                    if (!repository.isDuplicatedS(codeProduct)) {
                        addProductDB(
                                ModelEditProductS(
                                        codeProduct, name, FragmentsInfo.LAST_CODE_SESSION_SENDED, amount, buyPrice, salePrice, descr, statePhoto, deficit, size, brand
                                )
                        )
                    } else {
                        showAlertDialogDuplicated()
                    }
                }

            }
        }

        //Finished
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun addProductDB(productS: ModelEditProductS) {
        lifecycleScope.launch {
            repository.addProduct(
                    productS.c_productS,
                    productS.name,
                    FragmentsInfo.LAST_CODE_SESSION_SENDED,
                    productS.amount,
                    productS.buyPrice,
                    productS.salePrice,
                    productS.descr,
                    productS.deficit,
                    productS.size,
                    productS.brand,
                    productS.statePhoto
            )
        }

        al_editProduct.add(productS)
        if (productS.statePhoto == 1) {
            val bitmap = li_add_binding?.image?.drawable?.toBitmap()
            bitmap?.let { ImageTools.saveImageToInternalStorage(this, it, productS.c_productS) }
        }
        updateRecyclerAdapter()
        FancyToast.makeText(
                this,
                getString(R.string.Operacion_realizada_con_exito),
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
        ).show()

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
        val code = al_editProduct[position].c_productS
        val amount = al_editProduct[position].amount
        val buyPrice = al_editProduct[position].buyPrice
        val salePrice = al_editProduct[position].salePrice
        val descr = al_editProduct[position].descr
        val cProduct = al_editProduct[position].c_productS
        val deficit = al_editProduct[position].deficit
        val size = al_editProduct[position].size
        val brand = al_editProduct[position].brand
        var statePhoto = al_editProduct[position].statePhoto
        uriImageCut = null
        var uriToDelete: Uri? = null

        //Fill out
        if (statePhoto == 1) {
            val cw = ContextWrapper(this)
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            uriImageCut = File(directory, "${cProduct}.jpg").toUri()
            uriToDelete = File(directory, "${cProduct}.jpg").toUri()
        }
        li_add_binding?.tietName?.setText(name)
        li_add_binding?.tietCode?.setText(code)
        li_add_binding?.tietCode?.isEnabled = false
        li_add_binding?.tietCant?.setText(amount.toString())
        li_add_binding?.tietPriceBuy?.setText(buyPrice.toString())
        li_add_binding?.tietPriceSale?.setText(salePrice.toString())
        li_add_binding?.tietDesc?.setText(descr)
        li_add_binding?.tietSize?.setText(size)
        li_add_binding?.tietBrand?.setText(brand)
        li_add_binding?.image?.let {
            val cw = ContextWrapper(this)
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            if (statePhoto == 1) {
                Glide.with(this)
                        .load(File(directory, "${cProduct}.jpg"))
                        .error(R.drawable.widgets)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(it)
            }
            if (imageIsInProcess) {
                li_add_binding?.image?.setImageURI(uriImageCut)
                imageIsInProcess = false
            }
        }

        li_add_binding?.tietDeficit?.setText(deficit.toString())


        //Listener
        li_add_binding?.image?.setOnClickListener {
            val popupMenu = PopupMenu(this, li_add_binding?.image)
            popupMenu.menuInflater.inflate(R.menu.menu_image_add, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.menu_image_add) {
                    imageIsInProcess = true
                    choiceGalleryImage()
                } else if (menuItem.itemId == R.id.menu_image_del) {
                    li_add_binding?.image?.setImageDrawable(null)
                    uriImageCut = null
                }
                false
            }
            popupMenu.show()
        }
        li_add_binding?.btnCancel?.setOnClickListener {
            alertDialog.dismiss()
            uriImageCut = null
        }
        li_add_binding?.btnAccept?.setOnClickListener {
            if (checkInfoDataAdd()) {
                alertDialog.dismiss()
                statePhoto = if (uriImageCut == null) 0 else 1
                if (uriImageCut == null) {
                    if (uriToDelete?.isAbsolute == true) {
                        File(URI(uriToDelete.toString())).delete()
                    }
                }
                if (al_editProduct[position].fk_c_sessionS.contains(Constants.KEY_SALESPERSON_PRODUCT)) {
                    updateProductInternetLS(
                            ModelEditProductLS(
                                    al_editProduct[position].c_productS,
                                    li_add_binding?.tietName?.text.toString(),
                                    prepareForaing(al_editProduct[position].fk_c_sessionS),
                                    li_add_binding?.tietCant?.text.toString().toInt(),
                                    li_add_binding?.tietPriceBuy?.text.toString().toDouble(),
                                    li_add_binding?.tietPriceSale?.text.toString().toDouble(),
                                    li_add_binding?.tietDesc?.text.toString(),
                                    statePhoto,
                                    li_add_binding?.tietDeficit?.text.toString().toInt(),
                                    li_add_binding?.tietSize?.text.toString(),
                                    li_add_binding?.tietBrand?.text.toString()
                            )
                    )
                } else {
                    updateProductInternetS(
                            ModelEditProductS(
                                    al_editProduct[position].c_productS,
                                    li_add_binding?.tietName?.text.toString(),
                                    al_editProduct[position].fk_c_sessionS,
                                    li_add_binding?.tietCant?.text.toString().toInt(),
                                    li_add_binding?.tietPriceBuy?.text.toString().toDouble(),
                                    li_add_binding?.tietPriceSale?.text.toString().toDouble(),
                                    li_add_binding?.tietDesc?.text.toString(),
                                    statePhoto,
                                    li_add_binding?.tietDeficit?.text.toString().toInt(),
                                    li_add_binding?.tietSize?.text.toString(),
                                    li_add_binding?.tietBrand?.text.toString()
                            )
                    )
                }
            }
        }

        //Finished
        alertDialog.setCancelable(false)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun updateProductInternetS(product: ModelEditProductS) {
        lifecycleScope.launch {
            repository.updateProduct(
                    product.c_productS,
                    product.name,
                    product.amount,
                    product.buyPrice,
                    product.salePrice,
                    product.descr,
                    product.deficit,
                    product.statePhoto,
                    product.size,
                    product.brand
            )

            if (product.statePhoto == 1) {
                val bitmap = li_add_binding?.image?.drawable?.toBitmap()
                bitmap?.let { ImageTools.saveImageToInternalStorage(this@Activity_EditProduct, it, product.c_productS) }
            }
            loadRecyclerInfo()
        }

        FancyToast.makeText(
                this,
                getString(R.string.Operacion_realizada_con_exito),
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
        ).show()

    }

    private fun updateProductInternetLS(product: ModelEditProductLS) {
        lifecycleScope.launch {
            repository.updateProductLS(
                    product.c_productLS,
                    product.name,
                    product.amount,
                    product.buyPrice,
                    product.salePrice,
                    product.descr,
                    product.deficit,
                    product.size,
                    product.brand,
                    product.statePhoto
            )
            if (product.statePhoto == 1) {
                val bitmap = li_add_binding?.image?.drawable?.toBitmap()
                bitmap?.let { ImageTools.saveImageToInternalStorage(this@Activity_EditProduct, it, product.c_productLS) }
            }
            loadRecyclerInfo()
        }

        FancyToast.makeText(
                this,
                getString(R.string.Operacion_realizada_con_exito),
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
        ).show()

    }


    /*
    -------------Find Path
     */
    private fun fetchProductsPathInternet(position: Int) {
        lifecycleScope.launch {
            val alModelPath = repository.fetchProductSPath(
                    al_editProduct[position].c_productS
            )
            val path = makePath(alModelPath, position)
            showAlertDialogPath(path)
            updateRecyclerAdapter()
        }


        FancyToast.makeText(
                this@Activity_EditProduct,
                getString(R.string.Operacion_realizada_con_exito),
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
        ).show()
    }

    private fun fetchProductsPathInternetLS(position: Int) {
        lifecycleScope.launch {
            val alModelPathLS = repository.fetchProductLSPath(
                    al_editProduct[position].c_productS
            )
            val path = makePathLS(alModelPathLS, position)
            showAlertDialogPath(path)
            updateRecyclerAdapter()
        }


        FancyToast.makeText(
                this@Activity_EditProduct,
                getString(R.string.Operacion_realizada_con_exito),
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
        ).show()


    }

    private fun makePath(al_modelPath: MutableList<ModelProductPath>, position: Int): String {
        if (al_modelPath.isNotEmpty()) {
            val shelfCode = al_modelPath[0].c_shelfS

            val drawerCode = al_modelPath[0].c_drawerS
            val guionDrawerPosition = drawerCode.lastIndexOf("_")
            val newDrawerCode = drawerCode.substring(guionDrawerPosition + 1)

            val sessionCode = al_editProduct[position].fk_c_sessionS
            val guionSessionPosition = sessionCode.lastIndexOf("_")
            val newSessionCode = sessionCode.substring(guionSessionPosition + 1)




            return "$shelfCode/$newDrawerCode/$newSessionCode"

        } else {
            return getString(R.string.Se_ha_producido_un_error)
        }
    }

    private fun makePathLS(al_modelPath: MutableList<ModelProductPathLS>, position: Int): String {
        if (al_modelPath.isNotEmpty()) {
            val shelfCode = al_modelPath[0].c_shelfLS

            val drawerCode = al_modelPath[0].c_drawerLS
            val guionDrawerPosition = drawerCode.lastIndexOf("_")
            val newDrawerCode = drawerCode.substring(guionDrawerPosition + 1)

            val sessionCode = al_editProduct[position].fk_c_sessionS
            val guionSessionPosition = sessionCode.lastIndexOf("_")
            val newSessionCode = sessionCode.substring(guionSessionPosition + 1)




            return "$shelfCode/$newDrawerCode/$newSessionCode"

        } else {
            return getString(R.string.Se_ha_producido_un_error)
        }
    }


    /*
    *----------Choice Image
     */
    private fun choiceGalleryImage() {
        val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }

    private fun imageReceived(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val contentUri = data?.data
            val file = ImageTools.createTempImageFile(
                    this@Activity_EditProduct,
                    ImageTools.getHoraActual("yyMMddHHmmss")
            )
            if (contentUri != null) {
                cutImage(contentUri, Uri.fromFile(file))
            } else {
                Toast.makeText(
                        this@Activity_EditProduct,
                        R.string.error_obtener_imagen,
                        Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                    this@Activity_EditProduct,
                    R.string.error_obtener_imagen,
                    Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun cutImage(uri1: Uri, uri2: Uri) {
        try {
            UCrop.of(uri1, uri2)
                    .withAspectRatio(3f, 3f)
                    .withMaxResultSize(
                            ImageTools.ANCHO_DE_FOTO_A_SUBIR,
                            ImageTools.ALTO_DE_FOTO_A_SUBIR
                    )
                    .start(this)
        } catch (e: Exception) {
            Toast.makeText(
                    this@Activity_EditProduct,
                    getString(R.string.error),
                    Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun imageCropped(data: Intent?) {
        if (data != null) {
            uriImageCut = UCrop.getOutput(data)
            li_add_binding?.image?.setImageURI(uriImageCut)
        }
    }


    /*
    *----------Delete products
     */
    private fun deleteDBProductS(position: Int) {
        lifecycleScope.launch {
            repository.deleteProduct(
                    al_editProduct[position].c_productS,
                    al_editProduct[position].fk_c_sessionS
            )
            loadRecyclerInfo()
            val cw = ContextWrapper(this@Activity_EditProduct)
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            File(directory, "${al_editProduct[position].c_productS}.jpg").delete()
        }

        FancyToast.makeText(
                this,
                getString(R.string.Operacion_realizada_con_exito),
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
        ).show()


    }

    private fun deleteDBProductLS(position: Int) {
        lifecycleScope.launch {
            repository.deleteProductLS(
                    al_editProduct[position].c_productS,
                    prepareForaing(al_editProduct[position].fk_c_sessionS)
            )
            loadRecyclerInfo()
            val cw = ContextWrapper(this@Activity_EditProduct)
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            File(directory, "${al_editProduct[position].c_productS}.jpg").delete()
        }

        FancyToast.makeText(
                this,
                getString(R.string.Operacion_realizada_con_exito),
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
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
        builder.setMessage(R.string.Desea_eliminar_el_producto)
        //set listeners for dialog buttons
        builder.setPositiveButton(R.string.Si) { dialog, _ ->
            dialog.dismiss()
            if (al_editProduct[position].fk_c_sessionS.contains(Constants.KEY_SALESPERSON_PRODUCT)) {
                deleteDBProductLS(position)
            } else {
                deleteDBProductS(position)
            }

        }
        builder.setNegativeButton(R.string.No) { dialog, _ ->
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


    /*
    ------------Amount
     */
    private fun li_amount(position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_alter_amount_binding = LiAlterAmountBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_alter_amount_binding.root)
        val alertDialog = builder.create()

        //Filling and listeners
        var currentAmount: Int = al_editProduct.get(position).amount
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
                if (al_editProduct[position].fk_c_sessionS.contains(Constants.KEY_SALESPERSON_PRODUCT)) {
                    uploadAmountChangesInternetLS(
                            li_alter_amount_binding.et.text.toString().toInt(),
                            position
                    )
                } else {
                    uploadAmountChangesInternet(
                            li_alter_amount_binding.et.text.toString().toInt(),
                            position
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

    private fun uploadAmountChangesInternet(amount: Int, position: Int) {
        lifecycleScope.launch {


            repository.alterAmountS(
                    al_editProduct[position].c_productS,
                    amount
            )
        }



        FancyToast.makeText(
                this@Activity_EditProduct,
                getString(R.string.Operacion_realizada_con_exito),
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
        ).show()
        al_editProduct[position].amount = amount
        updateRecyclerAdapter()

    }

    private fun uploadAmountChangesInternetLS(amount: Int, position: Int) {
        lifecycleScope.launch {


            repository.alterAmountLS(
                    prepareForaing(al_editProduct[position].c_productS),
                    amount
            )
        }



        FancyToast.makeText(
                this@Activity_EditProduct,
                getString(R.string.Operacion_realizada_con_exito),
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
        ).show()
        al_editProduct[position].amount = amount
        updateRecyclerAdapter()

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
        var currentAmount: Int = al_editProduct.get(position).amount
        li_alter_amount_binding.et.setText(currentAmount.toString())

        li_alter_amount_binding.ivBtnMore.setOnClickListener(View.OnClickListener {
            if (currentAmount != al_editProduct.get(position).amount) {
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
            transferExist = repository.isDuplicatedLS(al_editProduct[position].c_productS)

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
        val fragment_shelvesLS = Fragment_ShelvesLS()
        fragment_shelvesLS.setOpenShelfLSListener(object : Fragment_ShelvesLS.OpenShelfLS {
            override fun onShelfLSClicked(c_shelfLS: String) {
                showFragmentDrawersLS(c_shelfLS, position, transferExist, transferAllSend, selectedAmount)
            }
        })
        fragmentManager.beginTransaction()
                .replace(binding.clTransferFrame.id, fragment_shelvesLS)
                .commit()
    }

    private fun showFragmentDrawersLS(c_shelfS: String, position: Int, transferExist: Boolean, transferAllSend: Boolean, selectedAmount: Int) {
        binding.clTransferToolbar.setTitle(R.string.Seleccione_Gaveta)
        FragmentsInfo.LAST_CODE_SHELVES_LS_SENDED = c_shelfS
        FragmentsInfo.LAST_FRAGMENT_TOUCHED = FragmentsInfo.Companion.EFragments.FR_DRAWERS
        val fragment_drawersLS = Fragment_DrawerLS(c_shelfS)
        fragment_drawersLS.setOpenDrawerLSListener(object : Fragment_DrawerLS.OpenDrawerLS {
            override fun onDrawerLSClicked(code: String) {
                showFragmentSessionsLS(code,c_shelfS, position, transferExist, transferAllSend, selectedAmount)
            }
        })
        fragmentManager.beginTransaction()
                .replace(binding.clTransferFrame.id, fragment_drawersLS)
                .commit()
    }

    private fun showFragmentSessionsLS(c_drawerS: String,c_shelfS: String, position: Int, transferExist: Boolean, transferAllSend: Boolean, selectedAmount: Int) {
        binding.clTransferToolbar.setTitle(R.string.Secciones)
        FragmentsInfo.LAST_CODE_DRAWER_LS_SENDED = c_drawerS
        FragmentsInfo.LAST_FRAGMENT_TOUCHED = FragmentsInfo.Companion.EFragments.FR_SESSION
        val fragment_sessionsLS = Fragment_SessionsLS(c_drawerS, c_shelfS)
        fragment_sessionsLS.setOpenSessionLSListener(object : Fragment_SessionsLS.OpenSessionLS {
            override fun onSessionLSClicked(c_sessionsLS: String) {
                binding.aepClTransfer.visibility = View.GONE
                if (al_editProduct[position].fk_c_sessionS.contains(Constants.KEY_SALESPERSON_PRODUCT)) {
                    FancyToast.makeText(
                            this@Activity_EditProduct,
                            getString(R.string.operacion_no_product_depend),
                            FancyToast.LENGTH_LONG,
                            FancyToast.WARNING,
                            false
                    ).show()
                } else {
                    transferProductBD(position, c_sessionsLS, transferExist, transferAllSend, selectedAmount)
                }
            }
        })
        fragmentManager.beginTransaction()
                .replace(binding.clTransferFrame.id, fragment_sessionsLS)
                .commit()
    }

    private fun transferProductBD(position: Int, codeSession: String, transferExist: Boolean, transferAllSend: Boolean, selectedAmount: Int) {
        lifecycleScope.launch {

            repository.transferProductS_LS(
                    al_editProduct[position].c_productS,
                    al_editProduct[position].name,
                    prepareForaing(al_editProduct[position].fk_c_sessionS),
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
                this@Activity_EditProduct,
                getString(R.string.Operacion_realizada_con_exito),
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
        ).show()

    }




    /** Inflated layouts Edit, Add and Info**/
    private fun li_infoProduct(position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_info_binding = LiInfoProductBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_info_binding?.root)
        val alertDialog = builder.create()

        //Init
        val name = getString(R.string.Producto_Info, al_editProduct[position].name)
        val code = getString(R.string.Codigo_Info, al_editProduct[position].c_productS)
        val amount = getString(R.string.Cantidad_Info, al_editProduct[position].amount)
        val buyPrice = getString(R.string.PrecioC_Info, al_editProduct[position].buyPrice)
        val salePrice = getString(R.string.PrecioV_Info, al_editProduct[position].salePrice)
        val descr = getString(R.string.Descripcion_Info, al_editProduct[position].descr)
        val size = getString(R.string.Size_Info, al_editProduct[position].size)
        val brand = getString(R.string.Brand_Info, al_editProduct[position].brand)
        val codeImage = al_editProduct[position].c_productS

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
                    .load(File(directory, "${al_editProduct[position].c_productS}.jpg"))
                    .error(R.drawable.widgets)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(it)
        }

        //Listeners
        li_info_binding?.close?.setOnClickListener { alertDialog.dismiss() }
        li_info_binding?.settings?.setOnClickListener {
            val popupMenu = PopupMenu(this@Activity_EditProduct, li_info_binding?.settings)
            popupMenu.menuInflater.inflate(R.menu.menu_product_options, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                alertDialog.dismiss()
                when (menuItem.itemId) {
                    R.id.option_cantidad -> li_amount(position)
                    R.id.option_editar -> liEditProduct(position)
                    R.id.option_transferir -> li_amountTransf(position)
                    R.id.option_eliminar -> showAlertDialogDeleteProducts(position)
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

    private fun findLocationClick(position: Int) {
        if (al_editProduct[position].fk_c_sessionS.contains(Constants.KEY_SALESPERSON_PRODUCT)) {
            fetchProductsPathInternetLS(position)
        } else {
            fetchProductsPathInternet(position)
        }
    }

    /**Activity Events**/
    private fun click_add() {
        liAddProduct()
    }


    /*
    *----------Upload info to the DB
     */


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
                imageCropped(data)
            } else {
                Toast.makeText(
                        this@Activity_EditProduct,
                        getString(R.string.error_obtener_imagen),
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
        if (requestCode == UCrop.RESULT_ERROR) {
            FancyToast.makeText(
                    this@Activity_EditProduct,
                    getString(R.string.error_obtener_imagen),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.ERROR,
                    false
            ).show()
        }
    }


    private fun prepareForaing(origin: String): String {
        return if (origin.contains(Constants.KEY_SALESPERSON_PRODUCT)) {
            origin.substring(Constants.KEY_SALESPERSON_PRODUCT.length)
        } else origin
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



}