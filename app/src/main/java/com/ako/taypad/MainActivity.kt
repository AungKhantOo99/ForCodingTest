package com.ako.taypad

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.QuickContactBadge
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.ako.taypad.ViewModel.AllData
import com.ako.taypad.databinding.ActivityMainBinding
import com.ako.taypad.model.example.bindexample
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import kotlin.math.absoluteValue
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    companion object{
        var allbook=ArrayList<bindexample>()
        lateinit var badge:BadgeDrawable
        var count=0
    }
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        supportActionBar?.hide()
//        val navView: BottomNavigationView = binding.navView
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        navView.setupWith    NavController(navController)

        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        navController=Navigation.findNavController(this,R.id.nav_host_fragment_activity_main)
        val buttonnav=findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.nav_view)
         badge = buttonnav.getOrCreateBadge(R.id.navigation_librsry)
        badge.isVisible = true
        buttonnav.let {
            NavigationUI.setupWithNavController(it,navController)
        }
        val viewmodel = ViewModelProvider(this).get(AllData::class.java)
        viewmodel.addcount(95)
        viewmodel.getcount().observe(this, Observer {
            badge.number=it
            count=it
        })
    }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do You Want to exist")
        builder.setPositiveButton("OK") { dialog, which ->
          finishAffinity()
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
        }
        builder.show()

    }

    override fun onStart() {
        super.onStart()

    }
}