package com.qnecesitas.elreteninventario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qnecesitas.elreteninventario.databinding.ActivityMenuAdminBinding

class Activity_MenuAdmin : AppCompatActivity() {
    private lateinit var binding: ActivityMenuAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.amaCvShelf.setOnClickListener { click_shelf() }

        binding.amaCvProducts.setOnClickListener { click_products() }

        binding.amaCvStatistics.setOnClickListener { click_statistics() }

        binding.amaCvOrders.setOnClickListener { click_orders() }

    }

    private fun click_shelf() {
        //TODO Aun no creado
    }

    private fun click_statistics() {
        //TODO Aun no creado
    }

    private fun click_products() {
        //TODO Aun no creado
    }

    private fun click_orders() {
        //TODO Aun no creado
    }

}