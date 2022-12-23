package com.ako.taypad.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.R
import com.ako.taypad.SearchResultActivity

class SearchAdapter(var context: Context, var item: ArrayList<String>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>(), Filterable {
    var filteritem = ArrayList<String>()

    //    init {
//        filteritem = item
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.searchitem, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        if (filteritem!!.isNotEmpty()) {
            holder.itemView.findViewById<TextView>(R.id.tags).text = filteritem!![position]
            holder.itemView.findViewById<TextView>(R.id.tags).setOnClickListener {
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("Key", filteritem[position])
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = filteritem!!.size
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                if (p0!!.isEmpty()) {
                    val result = ArrayList<String>()
                    filteritem = result
                } else {
                    val result = ArrayList<String>()
                    for (row in item) {
                        if (row.toLowerCase().contains(p0.toString().toLowerCase())) {
                            result.add(row)
                        }
                    }
                    filteritem = result
                }
                val Finalresult = FilterResults()
                Finalresult.values = filteritem
                return Finalresult
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteritem =
                    p1?.values as ArrayList<String> /* = java.util.ArrayList<com.ako.filtertest.model.products> */
                this@SearchAdapter.notifyDataSetChanged()
            }

        }

    }
}