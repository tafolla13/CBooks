package com.example.cbooks.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.cbooks.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        showSelectedFragment(HomeFragment())
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView!!.setOnNavigationItemReselectedListener { menuItem ->
            if(menuItem.itemId == R.id.inicio){
                showSelectedFragment(HomeFragment())
            }
            true
        }
    }

    private fun showSelectedFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.containerhome, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}
