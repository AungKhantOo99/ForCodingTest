package com.example.testproject.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testproject.ProductDetailActivity
import com.example.testproject.R
import com.example.testproject.model.products

class AllPhoneAdapter  (var context: Context, var item: ArrayList<products>):
    RecyclerView.Adapter<AllPhoneAdapter.ViewHolder>(), Filterable {
    var filteritem=ArrayList<products>()
    init {
        filteritem = item
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllPhoneAdapter.ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.phoneitems,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AllPhoneAdapter.ViewHolder, position: Int) {
        if(filteritem.isNotEmpty()){
            holder.itemView.findViewById<ImageView>(R.id.image).setImageResource(filteritem[position].image)
            holder.itemView.findViewById<TextView>(R.id.category).text=filteritem[position].name
            holder.itemView.findViewById<TextView>(R.id.price).text=filteritem[position].price.toString() +" MMK"
            holder.itemView.setOnClickListener {
                val intent= Intent(context, ProductDetailActivity::class.java)
                intent.putExtra("Keys",filteritem[position])
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = filteritem.size
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView)

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                if (p0!!.isEmpty()){
                    filteritem=item
                }else{
                    val result=ArrayList<products>()
                    for (row in item){
                        if(row.name.lowercase().contains(p0.toString().lowercase())||
                            row.stock.toString().contains(p0)){
                            result.add(row)
                            Log.d("Filter",row.name)
                        }
                    }
                    filteritem=result
                }
                val Finalresult= FilterResults()
                Finalresult.values=filteritem
                return Finalresult
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteritem=p1?.values as ArrayList<products>
                this@AllPhoneAdapter.notifyDataSetChanged()
            }

        }

    }
}
