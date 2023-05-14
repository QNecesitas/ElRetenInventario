package com.qnecesitas.elreteninventario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qnecesitas.elreteninventario.auxiliary.FragmentsInfo
import com.qnecesitas.elreteninventario.databinding.ActivityMenuAdminBinding

class Activity_MenuAdmin : AppCompatActivity() {
    private lateinit var binding: ActivityMenuAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.amaCvStore.setOnClickListener { click_store() }

        binding.amaCvProducts.setOnClickListener { click_products() }

        binding.amaCvStatistics.setOnClickListener { click_statistics() }

        binding.amaCvOrders.setOnClickListener { click_orders() }

        binding.settings.setOnClickListener{click_settings()}

        binding.cvProductsLs.setOnClickListener{click_products_ls()}

    }

    private fun click_store() {
        val intent = Intent(this, Activity_Store::class.java)
        startActivity(intent)
    }

    private fun click_statistics() {
        val intent = Intent(this, Statistics::class.java)
        startActivity(intent)
    }

    private fun click_products() {
        FragmentsInfo.LAST_CODE_SESSION_SENDED = "no"
        val intent = Intent(this, Activity_EditProduct::class.java)
        startActivity(intent)
    }

    private fun click_orders() {
        //TODO Aun no creado
    }

    private fun click_settings(){
        val intent = Intent(this, Activity_Settings::class.java)
        startActivity(intent)
    }

    private fun click_products_ls(){
        val intent = Intent(this, Activity_EditProductLS::class.java)
        startActivity(intent)
    }

}