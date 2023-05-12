package com.qnecesitas.elreteninventario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qnecesitas.elreteninventario.databinding.ActivityMenuSelespersonBinding

class Activity_MenuSelesperson : AppCompatActivity() {
    lateinit var binding: ActivityMenuSelespersonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMenuSelespersonBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.cvCounter.setOnClickListener{toCounter()}

        binding.cvOrders.setOnClickListener{toOrders()}

        binding.cvStore.setOnClickListener{toLittleStore()}

    }


    fun toLittleStore(){
        val intent = Intent(this, Activity_LittleStore::class.java)
        startActivity(intent)
    }

    fun toCounter(){
        val intent = Intent(this,Activity_Counter:: class.java )
        startActivity(intent)
    }

    fun toOrders(){
        val intent = Intent(this,Activity_Counter:: class.java )
        startActivity(intent)
    }


}