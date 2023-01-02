package com.ako.taypad.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.R
import com.ako.taypad.StoryDetailsActivity
import com.ako.taypad.model.getallstories.allstoriesItem
import com.squareup.picasso.Picasso

class SearchResultAdapter (val context: Context, val binddata:ArrayList<allstoriesItem>): RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultAdapter.ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.searchresultitem,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultAdapter.ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.titel).text=binddata[position].title
        holder.itemView.findViewById<TextView>(R.id.descrription).text=binddata[position].description
        holder.itemView.findViewById<TextView>(R.id.tags).text=binddata[position].category.name.toString()
       val img =holder.itemView.findViewById<ImageView>(R.id.cover)
        if(binddata[position].coverUrl!=null){
            Picasso.get().load("http://192.168.100.147:1337${binddata[position].coverUrl}")
                .into(img)
        }else{
            img.setImageResource(R.drawable.one)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, StoryDetailsActivity::class.java)
            intent.putExtra("Key", binddata[position].id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return binddata.size
    }

}