package com.qnecesitas.elreteninventario

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelPassword
import com.qnecesitas.elreteninventario.database.Repository
import com.qnecesitas.elreteninventario.databinding.ActivitySettingsBinding
import com.qnecesitas.elreteninventario.network.RetrofitPasswords
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Activity_Settings : AppCompatActivity() {

    //Bindind
    private lateinit var binding: ActivitySettingsBinding

    //Intenert
    private lateinit var alPassword: MutableList<ModelPassword>
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        //Refresh
        binding.refresh.setOnRefreshListener { loadPasswordInternet {} }

        binding.btnAdminAccept.setOnClickListener { clickAcceptAdmin() }

        binding.btnSalespAccept.setOnClickListener { clickAcceptSalesperson() }

        //Internet
        alPassword = ArrayList()
        repository = Repository(application as ElRetenApplication)
    }

    /*Listeners
     *-----------------------------------
     **/
    private fun clickAcceptAdmin() {
        if (checkInfoAdmin()) {
            if (alPassword.isEmpty()) {
                loadPasswordInternet { checkPasswordAdmin() }
            } else {
                checkPasswordAdmin()
            }
        }
    }

    private fun clickAcceptSalesperson() {
        if (checkInfoSalesperson()) {
            if (alPassword.isEmpty()) {
                loadPasswordInternet { checkPasswordSalesperson() }
            } else {
                checkPasswordSalesperson()
            }
        }
    }


    /*Auxiliary
     *-----------------------------------
     **/
    private fun checkInfoAdmin(): Boolean {
        var result = true

        if (binding.tietPasswAdminCurrent.text?.isEmpty() == true) {
            result = false
            binding.tietPasswAdminCurrent.error = getString(R.string.este_campo_no_debe_vacio)
        } else {
            binding.tietPasswAdminCurrent.error = null
        }

        if (binding.tietPasswAdminNew.text?.isEmpty() == true) {
            result = false
            binding.tietPasswAdminNew.error = getString(R.string.este_campo_no_debe_vacio)
        } else {
            binding.tietPasswAdminNew.error = null
        }

        if (binding.tietPasswAdminConfirm.text?.isEmpty() == true) {
            result = false
            binding.tietPasswAdminConfirm.error = getString(R.string.este_campo_no_debe_vacio)
        } else {
            binding.tietPasswAdminConfirm.error = null
        }

        if (binding.tietPasswAdminConfirm.text.toString() !=
            binding.tietPasswAdminNew.text.toString()
        ) {
            result = false
            binding.tietPasswAdminConfirm.error = getString(R.string.los_campos_no_coinciden)
        } else {
            if (binding.tietPasswAdminConfirm.text?.trim()?.isNotEmpty() == true) {
                binding.tietPasswAdminConfirm.error = null
            }
        }
        return result
    }

    private fun checkInfoSalesperson(): Boolean {
        var result = true

        if (binding.tietPasswSalespCurrent.text?.isEmpty() == true) {
            result = false
            binding.tietPasswSalespCurrent.error = getString(R.string.este_campo_no_debe_vacio)
        } else {
            binding.tietPasswSalespCurrent.error = null
        }

        if (binding.tietPasswSalespNew.text?.isEmpty() == true) {
            result = false
            binding.tietPasswSalespNew.error = getString(R.string.este_campo_no_debe_vacio)
        } else {
            binding.tietPasswSalespNew.error = null
        }

        if (binding.tietPasswSalespConfirm.text?.isEmpty() == true) {
            result = false
            binding.tietPasswSalespConfirm.error = getString(R.string.este_campo_no_debe_vacio)
        } else {
            binding.tietPasswSalespConfirm.error = null
        }

        if (binding.tietPasswSalespConfirm.text.toString() !=
            binding.tietPasswSalespNew.text.toString()
        ) {
            result = false
            binding.tietPasswSalespConfirm.error = getString(R.string.los_campos_no_coinciden)
        } else {
            binding.tietPasswSalespConfirm.error = null
        }

        return result
    }

    private fun checkPasswordAdmin() {
        val introducedPassword = binding.tietPasswAdminCurrent.text.toString()
        val newPassword = binding.tietPasswAdminNew.text.toString()
        val bdPassword = if (alPassword[0].user == "Administrador") {
            alPassword[0].password
        } else {
            alPassword[1].password
        }

        if (introducedPassword == bdPassword) {
            updatePassword("Administrador" , newPassword)
        } else {
            binding.tietPasswAdminCurrent.error = getString(R.string.Contrasena_incorrecta)
        }

    }

    private fun checkPasswordSalesperson() {
        val introducedPassword = binding.tietPasswSalespCurrent.text.toString()
        val newPassword = binding.tietPasswSalespNew.text.toString()
        val bdPassword = if (alPassword[0].user == "Dependiente") {
            alPassword[0].password
        } else {
            alPassword[1].password
        }

        if (introducedPassword == bdPassword) {
            updatePassword("Dependiente" , newPassword)
        } else {
            binding.tietPasswSalespCurrent.error = getString(R.string.Contrasena_incorrecta)
        }
    }

    /*Internet
     *-----------------------------------
     **/
    private fun loadPasswordInternet(callBack: () -> Unit) {
        lifecycleScope.launch {


            alPassword = repository.fetchAccounts()



            callBack()
        }

    }

    private fun updatePassword(user: String , password: String) {
        lifecycleScope.launch {


            repository.updateAccount(
                password ,
                user
            )
        }

        FancyToast.makeText(
            applicationContext ,
            getString(R.string.Operacion_realizada_con_exito) ,
            FancyToast.LENGTH_LONG ,
            FancyToast.SUCCESS ,
            false
        ).show()
        loadPasswordInternet { {} }
    }


}