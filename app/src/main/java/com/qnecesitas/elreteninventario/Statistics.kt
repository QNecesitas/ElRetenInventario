package com.qnecesitas.elreteninventario

import android.app.DatePickerDialog
import android.content.res.Resources
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import com.qnecesitas.elreteninventario.databinding.ActivityStatisticsBinding
import java.util.Calendar

class Statistics : AppCompatActivity() {

    lateinit var binding: ActivityStatisticsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.ivProfitDayCalendar.setOnClickListener {

        }

    }

    private fun composeProfit_Day(){

    }

}