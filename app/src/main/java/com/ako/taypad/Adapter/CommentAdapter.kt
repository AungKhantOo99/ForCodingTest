package com.ako.taypad.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.R
import com.ako.taypad.model.storypartsdata.Comment

class CommentAdapter (var context: Context):
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    private var arrayList:ArrayList<Comment> = ArrayList<Comment>()
    fun setlist(arrayList: ArrayList<Comment>){
        this.arrayList=arrayList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.commentitems,parent,false)
        return CommentAdapter.ViewHolder(view)
    }
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CommentAdapter.ViewHolder, position: Int) {
        holder.textzz.text=arrayList[position].comment
    }
    override fun getItemCount() = arrayList.size
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val textzz:TextView=itemView.findViewById(R.id.comments)
    }
}