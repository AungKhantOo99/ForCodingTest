package com.example.testproject.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testproject.NewsDetailActivity
import com.example.testproject.R
import com.example.testproject.model.Article
import com.squareup.picasso.Picasso

class NewsAdapter(val context:Context,private val binddata: ArrayList<Article>):
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.newsitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(binddata[position].author.equals(null)){
            holder.itemView.findViewById<TextView>(R.id.name).text="Unknown auther"
        }else{
            holder.itemView.findViewById<TextView>(R.id.name).text=binddata[position].author
        }

        Picasso.get().load(binddata[position].urlToImage)
            .into(holder.itemView.findViewById<ImageView>(R.id.image))
        holder.itemView.findViewById<TextView>(R.id.title).text=binddata[position].title
        val date:List<String> =binddata[position].publishedAt.split("T")
        holder.itemView.findViewById<TextView>(R.id.date).text=date[0]
        holder.itemView.setOnClickListener {
            val intent= Intent(context,NewsDetailActivity::class.java)
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            //  intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.putExtra("Keys",binddata[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
     return binddata.count()
    }

}