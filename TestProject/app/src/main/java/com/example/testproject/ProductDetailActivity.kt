package com.example.testproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.testproject.model.products

@Suppress("DEPRECATION")
class ProductDetailActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        supportActionBar!!.title="Products Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val name = findViewById<TextView>(R.id.name)
        val image = findViewById<ImageView>(R.id.image)
        val category = findViewById<TextView>(R.id.category)
        val price = findViewById<TextView>(R.id.price)
        val stock = findViewById<TextView>(R.id.stock)
        val warrenty = findViewById<TextView>(R.id.warrenty)
        val finger = findViewById<TextView>(R.id.finger)
        val camera = findViewById<TextView>(R.id.camera)
        val data=intent.getSerializableExtra("Keys") as products
        name.text=data.name
        image.setImageResource(data.image)
        category.text=data.brand
        price.text   =data.price.toString()
        stock.text   =data.stock.toString()
        if(!data.tag.warranty)warrenty.text="Not Include"
        else warrenty.text="Include Warranty"
        if(!data.tag.TripleCamera)camera.text="Not flagship Level"
        else camera.text="Flagship Level"
        if(!data.tag.InScreenFinger)finger.text= "Rear-mounted Finger Print"
        else finger.text="InScreen Finger"
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}