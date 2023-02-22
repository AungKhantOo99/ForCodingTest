package com.example.testproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.testproject.model.Article
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class NewsDetailActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        val actionBar = supportActionBar
        actionBar!!.title = "News Details"
        actionBar.setDisplayHomeAsUpEnabled(true)
        val image=findViewById<ImageView>(R.id.image)
        val newstitle=findViewById<TextView>(R.id.newstitle)
        val auther=findViewById<TextView>(R.id.auther)
        val source=findViewById<TextView>(R.id.source)
        val publishdate=findViewById<TextView>(R.id.publishdate)
        val decreption=findViewById<TextView>(R.id.decreption)
        val content=findViewById<TextView>(R.id.content)
        val moreinfo=findViewById<TextView>(R.id.moreinfo)
        val newsdetail = intent.getSerializableExtra("Keys") as Article
        Picasso.get().load(newsdetail.urlToImage).into(image)
        newstitle.text=newsdetail.title
        val date:List<String> =newsdetail.publishedAt.split("T")
        if(newsdetail.author.equals(null)){
            auther.text="Written By unknown auther"
        }else{
            auther.text="Writen By "+newsdetail.author
        }
        source.text= "Source : "+newsdetail.source.name
        publishdate.text="Published at : "+date[0]
        decreption.text=newsdetail.description
        content.text=newsdetail.content
        moreinfo.text=newsdetail.url
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}