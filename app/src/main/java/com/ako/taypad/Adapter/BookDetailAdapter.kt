package com.ako.taypad.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.Interface.bindselectbook
import com.ako.taypad.R
import com.ako.taypad.StoryDetailsActivity
import com.ako.taypad.model.example.bindexample

class BookDetailAdapter(val context: Context, val binddata:ArrayList<bindexample>,val bind:bindselectbook): RecyclerView.Adapter<BookDetailAdapter.ViewHolder>() {
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookDetailAdapter.ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.detailbook,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookDetailAdapter.ViewHolder, position: Int) {

        holder.itemView.findViewById<ImageView>(R.id.cover)
            .setImageResource(binddata[position].img)
        holder.itemView.setOnClickListener {
            bind.bindSelectBook(position)
        }
    }

    override fun getItemCount(): Int {
        return binddata.size
    }
}