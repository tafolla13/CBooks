package com.example.cbooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.cbooks.login.LoginFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFragment(LoginFragment())

    }

     private fun showFragment(fragment : Fragment){
         supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
             .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
             .commit()
    }
}
