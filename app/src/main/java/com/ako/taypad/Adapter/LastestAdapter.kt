package com.ako.taypad.Adapter

import android.annotation.SuppressLint
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

class LastestAdapter(val context: Context,val binddata:ArrayList<allstoriesItem>):RecyclerView.Adapter<LastestAdapter.ViewHolder>() {
    private var selectedItemPosition: Int = 0
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.lastest,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val img=holder.itemView.findViewById<ImageView>(R.id.lastestimg)
        if(binddata[position].coverUrl!=null){
            Picasso.get().load("http://192.168.100.147:1337${binddata[position].coverUrl}")
                .into(img)
        }else{
            img.setImageResource(R.drawable.one)
        }

        holder.itemView.findViewById<TextView>(R.id.tags).text="Freedom"

//        if(binddata[position].categories!=null){
//            holder.itemView.findViewById<TextView>(R.id.tags).text=
//                binddata[position].categories?.get(0)?.name
//        }else{
//            holder.itemView.findViewById<TextView>(R.id.tags).text="Freedom"
//
//        }
        holder.itemView.setOnClickListener {
            val intent= Intent(context, StoryDetailsActivity::class.java)
            intent.putExtra("Key",binddata[position].id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return binddata.size
    }
}