package com.ako.taypad.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.R
import com.ako.taypad.StoryDetailsActivity
import com.ako.taypad.ViewOtherProfileActivity
import com.ako.taypad.model.getallstories.allstoriesItem
import com.ako.taypad.ui.home.HomeFragment
import com.squareup.picasso.Picasso
import kotlin.random.Random

class HomeAdapter(val context: Context, private val binddata: ArrayList<allstoriesItem>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    init {
        setHasStableIds(true)
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.firstshow, parent, false)
        return ViewHolder(view)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
   //     val random=Random(binddata.size).nextInt(binddata.size)
        holder.itemView.findViewById<TextView>(R.id.titel).text = binddata[position].title
       val author= holder.itemView.findViewById<TextView>(R.id.author_name)
            if(binddata[position].author!=null){
                author.text=binddata[position].author!!.username
            }else{
                author.text="Unknown author"
            }
        val img=holder.itemView.findViewById<ImageView>(R.id.photo)
        if(binddata[position].coverUrl!=null){
            Picasso.get().load("http://192.168.100.212:1337${binddata[position].coverUrl}")
                .into(img)
        }else{
            img.setImageResource(R.drawable.one)
        }
        val profileimg=holder.itemView.findViewById<ImageView>(R.id.imgforprofile)
        val layout = holder.itemView.findViewById<RelativeLayout>(R.id.lastestlayout)
        val lastesrRecyclerView = holder.itemView.findViewById<RecyclerView>(R.id.lastest)
        val home_comment = holder.itemView.findViewById<LinearLayout>(R.id.home_comment)
        val more_option = holder.itemView.findViewById<ImageView>(R.id.more_option)
        val share = holder.itemView.findViewById<LinearLayout>(R.id.share)
        val like = holder.itemView.findViewById<LinearLayout>(R.id.like)
        profileimg.setOnClickListener {
            val intent=Intent(context,ViewOtherProfileActivity::class.java)
            intent.putExtra("id",binddata[position].author!!.id)
            context.startActivity(intent)
        }
        share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
              //  putExtra(Intent.EXTRA_TEXT, binddata[position].titel)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }
        like.setOnClickListener {
            holder.itemView.findViewById<TextView>(R.id.liketext).setText("Liked")
            holder.itemView.findViewById<ImageView>(R.id.likeimg)
                .setImageResource(R.drawable.ic_liked)
            val mp = MediaPlayer.create(context, R.raw.like)
            mp.start()
        }
        more_option.setOnClickListener {
            val popupMenu = PopupMenu(context, more_option)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.report ->
                        Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT)
                            .show()
                }
                true
            })
            popupMenu.show()
        }
        home_comment.setOnClickListener {
//            val dialog = BottomSheetDialog(context)
//            val view = LayoutInflater.from(context).inflate(R.layout.buttonsheet_comment, null)
//            val btnClose = view.findViewById<ImageView>(R.id.close)
//            btnClose.setOnClickListener {
//                dialog.dismiss()
//            }
//            dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
//            dialog.setContentView(view)
//            dialog.behavior.skipCollapsed = true
//            dialog.show()
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, StoryDetailsActivity::class.java)
            intent.putExtra("Key", binddata[position].id)
            context.startActivity(intent)
        }
        if (position == 4) {
            layout.visibility = View.VISIBLE
        }
        lastesrRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        lastesrRecyclerView.adapter = LastestAdapter(context, binddata)
    }

    override fun getItemCount(): Int {
        return binddata.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}