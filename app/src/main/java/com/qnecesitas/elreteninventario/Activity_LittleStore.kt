package com.qnecesitas.elreteninventario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.qnecesitas.elreteninventario.auxiliary.FragmentsInfo
import com.qnecesitas.elreteninventario.databinding.ActivityLittleStoreBinding

class Activity_LittleStore : AppCompatActivity() {
    lateinit var binding: ActivityLittleStoreBinding
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLittleStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toolbar
        setSupportActionBar(binding.ASToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.ASToolbar.setNavigationOnClickListener { onBack() }


        //Fragments
        fragmentManager = supportFragmentManager
        showFragmentShelves()


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBack()
            }
        })




    }

    private fun showFragmentShelves() {
        binding.ASToolbar.setTitle(R.string.Estantes)
        FragmentsInfo.LAST_FRAGMENT_TOUCHED = FragmentsInfo.Companion.EFragments.FR_SHELVES

        val fragment_shelves = Fragment_ShelvesLS()
        fragment_shelves.setOpenShelfLSListener(object : Fragment_ShelvesLS.OpenShelfLS {
            override fun onShelfLSClicked(c_shelfLS: String) {
                showFragmentDrawers(c_shelfLS)
            }

        })
        fragmentManager.beginTransaction()
            .replace(binding.asFrame.id, fragment_shelves)
            .commit()
    }

    private fun showFragmentDrawers(c_shelfS: String) {
        binding.ASToolbar.setTitle(R.string.Gavetas)
        FragmentsInfo.LAST_CODE_SHELVES_LS_SENDED = c_shelfS
        FragmentsInfo.LAST_FRAGMENT_TOUCHED = FragmentsInfo.Companion.EFragments.FR_DRAWERS

        val fragment_drawers = Fragment_DrawerLS(c_shelfS)
        fragment_drawers.setOpenDrawerLSListener(object : Fragment_DrawerLS.OpenDrawerLS {
            override fun onDrawerLSClicked(code: String) {
                showFragmentSessions(code)
            }

        })
        fragmentManager.beginTransaction()
            .replace(binding.asFrame.id, fragment_drawers)
            .commit()
    }

    private fun showFragmentSessions(c_drawerS: String) {
        binding.ASToolbar.setTitle(R.string.Secciones)
        FragmentsInfo.LAST_CODE_DRAWER_LS_SENDED = c_drawerS
        FragmentsInfo.LAST_FRAGMENT_TOUCHED = FragmentsInfo.Companion.EFragments.FR_SESSION

        val fragment_sessions = Fragment_SessionsLS(c_drawerS)
        fragment_sessions.setOpenSessionLSListener(object : Fragment_SessionsLS.OpenSessionLS {
            override fun onSessionLSClicked(c_sessionsLS: String) {
                FragmentsInfo.LAST_CODE_SESSION_LS_SENDED = c_sessionsLS
                val intent = Intent(this@Activity_LittleStore, Activity_EditProductLS::class.java)
                intent.putExtra("C_session", c_sessionsLS)
                startActivity(intent)
            }

        })
        fragmentManager.beginTransaction()
            .replace(binding.asFrame.id, fragment_sessions)
            .commit()
    }

    private fun onBack() {
        when (FragmentsInfo.LAST_FRAGMENT_TOUCHED) {
            FragmentsInfo.Companion.EFragments.FR_SHELVES -> finish()
            FragmentsInfo.Companion.EFragments.FR_DRAWERS -> showFragmentShelves()
            FragmentsInfo.Companion.EFragments.FR_SESSION -> showFragmentDrawers(FragmentsInfo.LAST_CODE_SHELVES_LS_SENDED)
            FragmentsInfo.Companion.EFragments.AC_PRODUCTS -> showFragmentSessions(FragmentsInfo.LAST_CODE_DRAWER_LS_SENDED)
        }
    }
}