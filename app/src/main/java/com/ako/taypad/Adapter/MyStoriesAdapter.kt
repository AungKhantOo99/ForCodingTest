package com.ako.taypad.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.MyStoriesPartsActivity
import com.ako.taypad.R
import com.ako.taypad.model.mystories.mystoriesItem
import com.squareup.picasso.Picasso

class MyStoriesAdapter (val  context:Context,val storiesdata:ArrayList<mystoriesItem>):
    RecyclerView.Adapter<MyStoriesAdapter.ViewHolder>(){
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.mystoriesview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title=holder.itemView.findViewById<TextView>(R.id.storytitel)
        val photo=holder.itemView.findViewById<ImageView>(R.id.storyphoto)
        val name=holder.itemView.findViewById<TextView>(R.id.author_name)
        val date=holder.itemView.findViewById<TextView>(R.id.date)
        title.text=storiesdata[position].title
        name.text=storiesdata[position].author.username
        val ary:List<String> = storiesdata[position].updatedAt.split("T")
        date.text=ary[0]
        if(storiesdata[position].coverUrl!=null){
            Picasso.get().load("http://192.168.100.147:1337${storiesdata[position].coverUrl}")
                .into(photo)
        }else{
            photo.setImageResource(R.drawable.one)
        }
        holder.itemView.setOnClickListener {
             val intent = Intent(context, MyStoriesPartsActivity::class.java)
            intent.putExtra("Key", storiesdata[position].id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return storiesdata.size
    }

}