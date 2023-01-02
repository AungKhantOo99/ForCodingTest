package com.ako.taypad.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.R
import com.ako.taypad.model.getpartsdata.Part
import com.squareup.picasso.Picasso

class MyPartsAdapter (val  context: Context, val partsdata:ArrayList<Part>):
    RecyclerView.Adapter<MyPartsAdapter.ViewHolder>() {
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.mypartsview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title=holder.itemView.findViewById<TextView>(R.id.parttitel)
        val partphoto=holder.itemView.findViewById<ImageView>(R.id.partphoto)
        title.text=partsdata[position].title
        if(partsdata[position].partCoverUrl !=null){
            Picasso.get().load("http://192.168.100.147:1337${partsdata[position].partCoverUrl}")
                .into(partphoto)
        }else{
            partphoto.setImageResource(R.drawable.one)
        }
    }

    override fun getItemCount(): Int {
      return partsdata.size
    }

}