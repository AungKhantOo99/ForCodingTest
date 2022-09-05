package com.ako.taypad.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.R
import com.ako.taypad.SearchResultActivity
import com.ako.taypad.StoryDetailsActivity
import com.ako.taypad.model.example.bindexample

class CategoryAdapter (var context: Context, var item: List<String>):
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.categoryitems,parent,false)
        return ViewHolder(view)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
           val btn= holder.itemView
               .findViewById<com.google.android.material.textview.MaterialTextView>(R.id.searchcategpry)
            btn.text= item!![position]
        btn.setBackgroundColor(androidx.appcompat.R.attr.colorPrimaryDark)
            btn.setOnClickListener {
                val intent= Intent(context, SearchResultActivity::class.java)
                intent.putExtra("Key",item[position])
                context.startActivity(intent)
        }
    }
    override fun getItemCount() = item.size
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView)
}