package com.qnecesitas.elreteninventario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.auxiliary.FragmentsInfo
import com.qnecesitas.elreteninventario.databinding.ActivityMenuSelespersonBinding

class Activity_MenuSelesperson : AppCompatActivity() {
    lateinit var binding: ActivityMenuSelespersonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMenuSelespersonBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.toolbar.setNavigationOnClickListener {finish()}

        binding.cvProduct.setOnClickListener{toProduct()}

        binding.cvCounter.setOnClickListener{toCounter()}

        binding.cvStore.setOnClickListener{toLittleStore()}

    }


    fun toLittleStore(){
        FragmentsInfo.STORE_ACCESS = FragmentsInfo.Companion.EAccess.Salesperson
        val intent = Intent(this, Activity_LittleStore::class.java)
        startActivity(intent)
    }

    fun toCounter(){
        val intent = Intent(this,Activity_Counter:: class.java )
        startActivity(intent)
    }

    fun toProduct(){
        FragmentsInfo.LAST_CODE_SESSION_LS_SENDED = "no"
        val intent = Intent(this,Activity_EditProductLS:: class.java )
        startActivity(intent)
    }


}