package com.example.testproject.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testproject.FilterResultActivity
import com.example.testproject.ProductDetailActivity
import com.example.testproject.R
import com.example.testproject.model.products

class FilterResultAdapter(val context: Context,val resultlist:ArrayList<products>):
    RecyclerView.Adapter<FilterResultAdapter.ViewHolder>() {

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.phoneitems, parent, false)
        return FilterResultAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<ImageView>(R.id.image).setImageResource(resultlist[position].image)
        holder.itemView.findViewById<TextView>(R.id.category).text=resultlist[position].name
        holder.itemView.findViewById<TextView>(R.id.price).text=resultlist[position].price.toString() +" MMK"
        holder.itemView.setOnClickListener {
            val intent= Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("Keys",resultlist[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount()=resultlist.size

}