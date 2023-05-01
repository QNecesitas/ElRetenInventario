package com.qnecesitas.elreteninventario

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.qnecesitas.elreteninventario.databinding.ActivityStoreBinding

class Activity_Store : AppCompatActivity() {

    lateinit var binding: ActivityStoreBinding
    lateinit var fragmentManager: FragmentManager

    //Fragments
    enum class EFragments{ FR_SHELVES, FR_DRAWERS, FR_SESSION }
    lateinit var current_fragment: EFragments
    lateinit var currentCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)



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
        current_fragment = EFragments.FR_SHELVES
        currentCode = "no"
        val fragment_shelves = Fragment_Shelves()
        fragment_shelves.setOpenShelfSListener(object : Fragment_Shelves.OpenShelfS{
            override fun onShelfSClicked(c_shelfS: String) {
                showFragmentDrawers(c_shelfS)
            }

        })
        fragmentManager.beginTransaction()
            .replace(binding.asFrame.id, fragment_shelves)
            .commit()
    }

    private fun showFragmentDrawers(c_shelfS : String)  {
        currentCode = c_shelfS
        current_fragment = EFragments.FR_DRAWERS
        val fragment_drawers = Fragment_Drawers(c_shelfS)
        fragment_drawers.setOpenDrawerSListener(object : Fragment_Drawers.OpenDrawerS{
            override fun onDrawerSClicked(code: String) {
                showFragmentSessions(code)
            }

        })
        fragmentManager.beginTransaction()
            .replace(binding.asFrame.id, fragment_drawers)
            .commit()
    }

    private fun showFragmentSessions(c_drawerS : String) {
        currentCode = c_drawerS
        current_fragment = EFragments.FR_SESSION
        val fragment_sessions = Fragment_Sessions(c_drawerS)
        fragment_sessions.setOpenSessionListener(object : Fragment_Sessions.OpenSession{
            override fun onSessionClicked(c_sessionS: String) {
                val intent: Intent = Intent(this@Activity_Store, Activity_EditProduct::class.java)
                intent.putExtra("C_session",c_sessionS)
                startActivity(intent)
            }

        })
        fragmentManager.beginTransaction()
            .replace(binding.asFrame.id, fragment_sessions)
            .commit()
    }

    private fun onBack() {
        when (current_fragment) {
            EFragments.FR_SHELVES -> finish()
            EFragments.FR_DRAWERS -> showFragmentShelves()
            EFragments.FR_SESSION -> showFragmentDrawers(currentCode)
        }
    }

}