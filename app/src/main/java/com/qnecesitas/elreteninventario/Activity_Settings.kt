package com.qnecesitas.elreteninventario

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.auxiliary.NetworkTools
import com.qnecesitas.elreteninventario.data.ModelPassword
import com.qnecesitas.elreteninventario.databinding.ActivitySettingsBinding
import com.qnecesitas.elreteninventario.network.RetrofitPasswords
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Activity_Settings : AppCompatActivity() {

    //Bindind
    private lateinit var binding: ActivitySettingsBinding

    //Intenert
    private lateinit var alPassword: ArrayList<ModelPassword>
    private lateinit var retrofitPasswords: RetrofitPasswords

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.toolbar.setNavigationOnClickListener {finish()}

        //Refresh
        binding.refresh.setOnRefreshListener { loadPasswordInternet {} }

        binding.btnAdminAccept.setOnClickListener{clickAcceptAdmin()}

        binding.btnSalespAccept.setOnClickListener{clickAcceptSalesperson()}

        //Internet
        alPassword = ArrayList()
        retrofitPasswords = RetrofitPasswords()
    }

    /*Listeners
     *-----------------------------------
     **/
    private fun clickAcceptAdmin(){
        if(checkInfoAdmin()){
            if(alPassword.isEmpty()){
                loadPasswordInternet{checkPasswordAdmin()}
            }else{
                checkPasswordAdmin()
            }
        }
    }

    private fun clickAcceptSalesperson(){
        if(checkInfoSalesperson()){
            if(alPassword.isEmpty()){
                loadPasswordInternet{checkPasswordSalesperson()}
            }else{
                checkPasswordSalesperson()
            }
        }
    }


    /*Auxiliary
     *-----------------------------------
     **/
    private fun checkInfoAdmin(): Boolean{
        var result = true

        if(binding.tietPasswAdminCurrent.text?.isEmpty() == true){
            result =false
            binding.tietPasswAdminCurrent.error = getString(R.string.este_campo_no_debe_vacio)
        }else{
            binding.tietPasswAdminCurrent.error = null
        }

        if(binding.tietPasswAdminNew.text?.isEmpty() == true){
            result =false
            binding.tietPasswAdminNew.error = getString(R.string.este_campo_no_debe_vacio)
        }else{
            binding.tietPasswAdminNew.error = null
        }

        if(binding.tietPasswAdminConfirm.text?.isEmpty() == true){
            result =false
            binding.tietPasswAdminConfirm.error = getString(R.string.este_campo_no_debe_vacio)
        }else{
            binding.tietPasswAdminConfirm.error = null
        }

        if(binding.tietPasswAdminConfirm.text.toString() !=
            binding.tietPasswAdminNew.text.toString()){
            result =false
            binding.tietPasswAdminConfirm.error = getString(R.string.los_campos_no_coinciden)
        }else{
            if(binding.tietPasswAdminConfirm.text?.trim()?.isNotEmpty()==true){
                binding.tietPasswAdminConfirm.error = null
            }
        }
        return result
    }

    private fun checkInfoSalesperson(): Boolean{
        var result = true

        if(binding.tietPasswSalespCurrent.text?.isEmpty() == true){
            result =false
            binding.tietPasswSalespCurrent.error = getString(R.string.este_campo_no_debe_vacio)
        }else{
            binding.tietPasswSalespCurrent.error = null
        }

        if(binding.tietPasswSalespNew.text?.isEmpty() == true){
            result =false
            binding.tietPasswSalespNew.error = getString(R.string.este_campo_no_debe_vacio)
        }else{
            binding.tietPasswSalespNew.error = null
        }

        if(binding.tietPasswSalespConfirm.text?.isEmpty() == true){
            result =false
            binding.tietPasswSalespConfirm.error = getString(R.string.este_campo_no_debe_vacio)
        }else{
            binding.tietPasswSalespConfirm.error = null
        }

        if(binding.tietPasswSalespConfirm.text.toString() !=
            binding.tietPasswSalespNew.text.toString()){
            result =false
            binding.tietPasswSalespConfirm.error = getString(R.string.los_campos_no_coinciden)
        }else{
            binding.tietPasswSalespConfirm.error = null
        }

        return result
    }

    private fun checkPasswordAdmin(){
        val introducedPassword = binding.tietPasswAdminCurrent.text.toString()
        val newPassword = binding.tietPasswAdminNew.text.toString()
        val bdPassword = if (alPassword[0].user == "Administrador") {
            alPassword[0].password
        } else {
            alPassword[1].password
        }

        if(introducedPassword == bdPassword){
            updatePassword("Administrador", newPassword)
        }else{
            binding.tietPasswAdminCurrent.error = getString(R.string.Contrasena_incorrecta)
        }

    }

    private fun checkPasswordSalesperson(){
        val introducedPassword = binding.tietPasswSalespCurrent.text.toString()
        val newPassword = binding.tietPasswSalespNew.text.toString()
        val bdPassword = if (alPassword[0].user == "Dependiente") {
            alPassword[0].password
        } else {
            alPassword[1].password
        }

        if(introducedPassword == bdPassword){
            updatePassword("Dependiente", newPassword)
        }else{
            binding.tietPasswSalespCurrent.error = getString(R.string.Contrasena_incorrecta)
        }
    }

    /*Internet
     *-----------------------------------
     **/
    private fun loadPasswordInternet(callBack: ()->Unit) {
        if (NetworkTools.isOnline(this@Activity_Settings, false)) {
            binding.refresh.isRefreshing = true

            val call = retrofitPasswords.fetchAccounts(Constants.PHP_TOKEN)

            call.enqueue(object : Callback<ArrayList<ModelPassword>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelPassword>>,
                    response: Response<ArrayList<ModelPassword>>
                ) {
                    binding.refresh.isRefreshing = false
                    if (response.isSuccessful) {
                        alPassword = response.body()!!
                        callBack()
                    } else {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.Revise_su_conexion),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<ModelPassword>>, t: Throwable) {
                    binding.refresh.isRefreshing = false
                    Snackbar.make(
                        binding.root,
                        getString(R.string.Revise_su_conexion),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            })
        } else {
            Snackbar.make(
                binding.root,
                getString(R.string.Revise_su_conexion),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun updatePassword(user: String, password: String){
        if (NetworkTools.isOnline(binding.root.context, true)) {
            binding.refresh.isRefreshing = true
            val call = retrofitPasswords.updateAccount(
                Constants.PHP_TOKEN,
                password,
                user
            )
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    binding.refresh.isRefreshing = false
                    if (response.isSuccessful) {
                        FancyToast.makeText(
                            applicationContext,
                            getString(R.string.Operacion_realizada_con_exito),
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false
                        ).show()
                        loadPasswordInternet { {} }
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
                    binding.refresh.isRefreshing = false
                }

            })
        }else{
            FancyToast.makeText(
                applicationContext,
                getString(R.string.Revise_su_conexion),
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,
                false
            ).show()
        }
    }



}