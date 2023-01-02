package com.ako.taypad

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout

class toolbarcheck : AppCompatActivity() {
    lateinit var toolbarLayout: CollapsingToolbarLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbarcheck)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
       toolbarLayout=findViewById(R.id.toolbarlayout)
    //    val  typeface = Typeface.createFromAsset(this.getAssets(), "fonts/FruitererLTStd-Light.otf");
        toolbarLayout.title="Htoo"
//        toolbarLayout.setCollapsedTitleTypeface(typeface)
//        toolbarLayout.setExpandedTitleTypeface(typeface)
    }
}