package com.qnecesitas.elreteninventario

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import com.qnecesitas.elreteninventario.data.ModelEditProductS
import com.qnecesitas.elreteninventario.data.ModelPassword
import com.qnecesitas.elreteninventario.database.Repository
import com.qnecesitas.elreteninventario.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class Activity_Login : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    private lateinit var repository: Repository
    private lateinit var al_password: MutableList<ModelPassword>
    private var countBadPassword = 0

    //Deficit
    private var alProductsDeficitS: ArrayList<ModelEditProductS>? = null
    private var alProductsDeficitLS: ArrayList<ModelEditProductS>? = null

    //Notification
    private val CHANNEL_ID = "ELReten"
    private val CHANNEL_NAME = "EL Retén"
    private val NOTIFICATION_ID1 = 123
    private val NOTIFICATION_ID2 = 234
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        repository = Repository(application as ElRetenApplication)
        al_password = ArrayList()
        binding!!.ALTIETPassword.setOnKeyListener { view: View , i: Int , keyEvent: KeyEvent ->
            eventKeyboard(
                view ,
                keyEvent
            )
        }
        binding!!.ALBTNStartSession.setOnClickListener { view: View? -> click_login() }
        alProductsDeficitS = ArrayList()
        alProductsDeficitLS = ArrayList()
        binding!!.tvAboutDev.setOnClickListener { view: View? -> click_dev() }
        binding!!.tvAboutUs.setOnClickListener { view: View? -> click_us() }
    }

    private fun eventKeyboard(view: View , keyEvent: KeyEvent): Boolean {
        if (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken , 0)
        }
        return false
    }

    private fun click_login() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding!!.ALTIETPassword.windowToken , 0)
        if (al_password.isEmpty()) {
            loadPasswordInternet()
        } else {
            checkPassword()
        }
    }

    private fun loadPasswordInternet() {
        lifecycleScope.launch {
            al_password = repository.fetchAccounts()
            if (!al_password.isEmpty()) {
                checkPassword()
            }
        }
    }

    private fun click_dev() {
        val intent = Intent(this , Activity_AboutDev::class.java)
        startActivity(intent)
    }

    private fun click_us() {
        val intent = Intent(this , Activity_AboutUs::class.java)
        startActivity(intent)
    }

    private fun checkPassword() {
        if (!binding!!.ALTIETPassword.text.toString().isEmpty()) {
            val user = if (binding!!.ALRBAdministrator.isChecked) "Administrador" else "Dependiente"
            val bdPassword: String
            bdPassword = if (al_password!![0].user == user) {
                al_password[0].password
            } else {
                al_password[1].password
            }
            val inputPassword = binding!!.ALTIETPassword.text.toString()
            if (bdPassword == inputPassword) {
                if (user == "Administrador") {
                    loadDeficitInternetS()
                    binding!!.ALTILPassword.error = null
                } else {
                    binding!!.ALTILPassword.error = null
                    val intent = Intent(this@Activity_Login , Activity_MenuSelesperson::class.java)
                    startActivity(intent)
                }
            } else {
                countBadPassword++
                binding!!.ALTILPassword.error = getString(R.string.Contrasena_incorrecta)
                if (countBadPassword == 5) showAlertDialogClosePassword()
            }
        } else {
            binding!!.ALTILPassword.error = getString(R.string.este_campo_no_debe_vacio)
        }
    }

    private fun loadDeficitInternetS() {
        //TODO
        //alProductsDeficitS = repository.fetchProductsDeficit("Almacén")
        loadDeficitInternetLS()
    }

    private fun loadDeficitInternetLS() {
        // TODO
        //alProductsDeficitLS = repository.fetchProductsDeficit("Mostrador")
        notifyNews()
    }

    private fun notifyNews() {
        if (!alProductsDeficitS!!.isEmpty()) {
            displayNotificationS(alProductsDeficitS!!.size)
        }
        if (!alProductsDeficitLS!!.isEmpty()) {
            displayNotificationLS(alProductsDeficitLS!!.size)
        }

        //Finish activity
        val intent = Intent(this@Activity_Login , Activity_MenuAdmin::class.java)
        startActivity(intent)
    }

    private fun displayNotificationS(amount: Int) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID , CHANNEL_NAME , NotificationManager.IMPORTANCE_HIGH)
            channel.description = getString(R.string.channel_decr)
            channel.enableLights(true)
            channel.lightColor = Color.RED
            notificationManager.createNotificationChannel(channel)
        }
        val intent = Intent(applicationContext , Activity_Deficit::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            applicationContext ,
            0 ,
            intent ,
            PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(applicationContext , CHANNEL_ID)
            .setContentTitle(getString(R.string.Productos_en_deficit))
            .setContentText(getString(R.string.deficit_almacen_admin , amount))
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .addAction(
                android.R.drawable.ic_menu_view ,
                getString(R.string.ver_deficit) ,
                pendingIntent
            )
        notificationManager.notify(NOTIFICATION_ID1 , builder.build())
    }

    private fun displayNotificationLS(amount: Int) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID , CHANNEL_NAME , NotificationManager.IMPORTANCE_HIGH)
            channel.description = getString(R.string.channel_decr)
            channel.enableLights(true)
            channel.lightColor = Color.RED
            notificationManager.createNotificationChannel(channel)
        }
        val intent = Intent(applicationContext , Activity_Deficit::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            applicationContext ,
            0 ,
            intent ,
            PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(applicationContext , CHANNEL_ID)
            .setContentTitle(getString(R.string.Productos_en_deficit))
            .setContentText(getString(R.string.deficit_almacen_salesperson , amount))
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .addAction(
                android.R.drawable.ic_menu_view ,
                getString(R.string.ver_deficit) ,
                pendingIntent
            )
        notificationManager.notify(NOTIFICATION_ID2 , builder.build())
    }

    override fun onBackPressed() {
        showAlertDialogExit()
    }

    private fun showAlertDialogExit() {
        //init alert dialog
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setTitle(R.string.salir)
        builder.setMessage(R.string.seguro_desea_salir)
        //set listeners for dialog buttons
        builder.setPositiveButton(R.string.Si) { dialog: DialogInterface? , which: Int ->
            //finish the activity
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        builder.setNegativeButton(R.string.no) { dialog: DialogInterface , which: Int ->
            //dialog gone
            dialog.dismiss()
        }
        //create the alert dialog and show it
        builder.create().show()
    }

    private fun showAlertDialogClosePassword() {
        //init alert dialog
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setTitle(R.string.intentos_fallidos)
        builder.setMessage(R.string.cerrar_password)
        //set listeners for dialog buttons
        builder.setPositiveButton(R.string.Cerrar) { dialog: DialogInterface? , which: Int ->
            //finish the activity
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        //create the alert dialog and show it
        builder.create().show()
    }
}