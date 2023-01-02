package com.ako.taypad

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.*
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView.*
import com.ako.taypad.databinding.ActivityMainBinding
import com.ako.taypad.ui.home.HomeFragment
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var badge: BadgeDrawable
        var count = 0
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityMainBinding
    }
    lateinit var buttonnav: com.google.android.material.bottomnavigation.BottomNavigationView
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)
        buttonnav = findViewById(R.id.nav_view)
        buttonnav.let {
            NavigationUI.setupWithNavController(it, navController)
        }
        if(checkConnection()!=true){
            val snackbar = Snackbar.make(
                binding.root, "No Internet Connection",
                Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction("Retry"){
                    snackbar.dismiss()
                }
            val snackbarView = snackbar.view
            val action =
                snackbarView.findViewById(com.google.android.material.R.id.snackbar_action) as TextView
            action.isAllCaps=false
            action.textSize=15f
            val textView =
                snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.textSize = 14f
            snackbar.show()
        }
        Log.d("current",buttonnav.selectedItemId.toString())
        buttonnav.setOnItemReselectedListener {
            Handler().postDelayed({ HomeFragment.recycler.scrollToPosition(0) }, 200)
          //  HomeFragment.layoutManager.scrollToPositionWithOffset(0,20)
        //    Toast.makeText(applicationContext,"Double Click",Toast.LENGTH_SHORT).show()
        }
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
    fun checkConnection(): Boolean? {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo?=connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected
    }
//    override fun onStart() {
//        super.onStart()
//
//    }
}