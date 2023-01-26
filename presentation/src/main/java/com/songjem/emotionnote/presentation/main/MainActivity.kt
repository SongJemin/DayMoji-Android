package com.songjem.emotionnote.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.songjem.emotionnote.R
import com.songjem.emotionnote.base.BaseActivity
import com.songjem.emotionnote.databinding.ActivityMainBinding
import com.songjem.emotionnote.presentation.main.calendar.CalendarFragment
import com.songjem.emotionnote.presentation.main.dashboard.DashboardFragment
import com.songjem.emotionnote.presentation.main.record.RecordActivity
import com.songjem.emotionnote.presentation.test.TestActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var calendarFragment: CalendarFragment
    private lateinit var dashboardFragment: DashboardFragment

    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = mainViewModel
        calendarFragment = CalendarFragment()
        dashboardFragment = DashboardFragment()

        supportFragmentManager.beginTransaction().replace(R.id.fl_container_main, calendarFragment).commit()

        binding.btnTestpageMain.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }

        val bottomNavigationView = findViewById<NavigationBarView>(R.id.bn_navigator_main)
        bottomNavigationView.setOnItemSelectedListener (
            object: NavigationBarView.OnItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when(item.itemId) {
                        R.id.item_calendar_menu -> return(changeFragment(calendarFragment))
                        R.id.item_dashboard_menu -> return(changeFragment(dashboardFragment))
//                        R.id.item_record_menu -> startRecord()
                    }
                    return false
                }
            }
        )
    }

    private fun changeFragment(selectedFragment: Fragment?) : Boolean {
        selectedFragment?.let {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_container_main, selectedFragment)
                .commit()
            return true
        }
        return false
    }

    private fun startRecord() {
        val intent = Intent(applicationContext, RecordActivity::class.java)
        startActivity(intent)
    }
}