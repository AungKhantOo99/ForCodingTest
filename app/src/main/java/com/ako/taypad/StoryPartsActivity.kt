package com.ako.taypad

import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import jp.wasabeef.richeditor.RichEditor


class StoryPartsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_parts)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        val richtest=findViewById<RichEditor>(R.id.rich)
        val scrollview=findViewById<ScrollView>(R.id.scoll)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        richtest.setPlaceholder("Start Writing")
        richtest.focusEditor()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}