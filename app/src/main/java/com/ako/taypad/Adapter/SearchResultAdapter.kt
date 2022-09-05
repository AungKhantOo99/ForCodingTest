package com.ako.taypad.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.R
import com.ako.taypad.model.example.bindexample

class SearchResultAdapter (val context: Context, val binddata:ArrayList<bindexample>): RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultAdapter.ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.searchresultitem,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultAdapter.ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.titel).text=binddata[position].titel.toString()
        holder.itemView.findViewById<TextView>(R.id.descrription).text=binddata[position].description
        holder.itemView.findViewById<TextView>(R.id.tags).text=binddata[position].tags
        holder.itemView.findViewById<ImageView>(R.id.cover)
            .setImageResource(binddata[position].img)
    }

    override fun getItemCount(): Int {
        return binddata.size
    }

}