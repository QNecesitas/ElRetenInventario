package com.qnecesitas.elreteninventario

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.qnecesitas.elreteninventario.auxiliary.FragmentsInfo
import com.qnecesitas.elreteninventario.databinding.ActivityStoreBinding

class Activity_Store : AppCompatActivity() {

    lateinit var binding: ActivityStoreBinding
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
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

        val intentService : Intent = Intent(this,ServiceExample::class.java)
        startService(intentService)
    }

    override fun onDestroy() {
        super.onDestroy()
        val broadcastIntent = Intent(this, BroadcastRestartService::class.java)
        broadcastIntent.action = "restart_service"
        sendBroadcast(broadcastIntent)
    }

    private fun showFragmentShelves() {
        binding.ASToolbar.setTitle(R.string.Estantes)
        FragmentsInfo.LAST_FRAGMENT_TOUCHED = FragmentsInfo.Companion.EFragments.FR_SHELVES
        val fragment_shelves = Fragment_Shelves()
        fragment_shelves.setOpenShelfSListener(object : Fragment_Shelves.OpenShelfS {
            override fun onShelfSClicked(c_shelfS: String) {
                showFragmentDrawers(c_shelfS)
            }
        })
        fragmentManager.beginTransaction()
            .replace(binding.asFrame.id, fragment_shelves)
            .commit()
    }

    private fun showFragmentDrawers(c_shelfS: String) {
        binding.ASToolbar.setTitle(R.string.Gavetas)
        FragmentsInfo.LAST_CODE_SHELVES_SENDED = c_shelfS
        FragmentsInfo.LAST_FRAGMENT_TOUCHED = FragmentsInfo.Companion.EFragments.FR_DRAWERS
        val fragment_drawers = Fragment_Drawers(c_shelfS)
        fragment_drawers.setOpenDrawerSListener(object : Fragment_Drawers.OpenDrawerS {
            override fun onDrawerSClicked(code: String) {
                showFragmentSessions(code)
            }

        })
        fragmentManager.beginTransaction()
            .replace(binding.asFrame.id, fragment_drawers)
            .commit()
    }

    private fun showFragmentSessions(c_drawerS: String) {
        binding.ASToolbar.setTitle(R.string.Secciones)
        FragmentsInfo.LAST_CODE_DRAWER_SENDED = c_drawerS
        FragmentsInfo.LAST_FRAGMENT_TOUCHED = FragmentsInfo.Companion.EFragments.FR_SESSION
        val fragment_sessions = Fragment_Sessions(c_drawerS)
        fragment_sessions.setOpenSessionListener(object : Fragment_Sessions.OpenSession {
            override fun onSessionClicked(c_sessions: String) {
                FragmentsInfo.LAST_CODE_SESSION_SENDED = c_sessions
                val intent = Intent(this@Activity_Store, Activity_EditProduct::class.java)
                intent.putExtra("C_session", c_sessions)
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
            FragmentsInfo.Companion.EFragments.FR_SESSION -> showFragmentDrawers(FragmentsInfo.LAST_CODE_SHELVES_SENDED)
            FragmentsInfo.Companion.EFragments.AC_PRODUCTS -> showFragmentSessions(FragmentsInfo.LAST_CODE_DRAWER_SENDED)
        }
    }

}