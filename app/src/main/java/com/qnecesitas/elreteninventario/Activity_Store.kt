package com.qnecesitas.elreteninventario

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.qnecesitas.elreteninventario.databinding.ActivityStoreBinding

class Activity_Store : AppCompatActivity() {

    lateinit var binding: ActivityStoreBinding
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Fragments
        fragmentManager = supportFragmentManager
        showFragmentShelves()

    }


    private fun showFragmentShelves() {
        val fragment_shelves = Fragment_Shelves()
        fragmentManager.beginTransaction()
            .replace(binding.asFrame.id, fragment_shelves)
            .commit()
    }

    private fun showFragmentDrawers() {
        val fragment_drawers = Fragment_Drawers()
        fragmentManager.beginTransaction()
            .replace(binding.asFrame.id, fragment_drawers)
            .commit()
    }

    private fun showFragmentSessions() {
        val fragment_sessions = Fragment_Sessions()
        fragmentManager.beginTransaction()
            .replace(binding.asFrame.id, fragment_sessions)
            .commit()
    }


}