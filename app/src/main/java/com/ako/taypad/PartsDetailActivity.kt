package com.ako.taypad

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.Adapter.CommentAdapter
import com.ako.taypad.Interface.StoryParts
import com.ako.taypad.Retrofit.RetrofitClient
import com.ako.taypad.ViewModel.AllData
import com.ako.taypad.ViewModel.PartsViewModel
import com.ako.taypad.model.comment.Data
import com.ako.taypad.model.comment.commentdata
import com.ako.taypad.model.comment.commetresponce
import com.ako.taypad.model.like.likeresponce
import com.ako.taypad.model.storypartsdata.responsepartdata
import com.ako.taypad.repository.PartsDateRepository
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PartsDetailActivity : AppCompatActivity(){
    lateinit var viewModel: AllData
    lateinit var allParts: PartsViewModel
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var parts: TextView
    lateinit var drawer: DrawerLayout
    lateinit var nav_view: NavigationView
    lateinit var show: Animation
    lateinit var hide: Animation
    lateinit var buttonaction: LinearLayout
    lateinit var coverimage: ImageView
    lateinit var partstitel: TextView
    lateinit var share: LinearLayout
    lateinit var photoforparts: ImageView
    lateinit var titelforparts: TextView
    lateinit var comment: LinearLayout
    lateinit var likepart: LinearLayout
    lateinit var likecount: TextView
    lateinit var commentcount: TextView
    lateinit var readcount: TextView
    lateinit var commentview: RelativeLayout
    lateinit var mainview: RelativeLayout
    var jwt: String? = ""
    var story:String?=""
    private var data=ArrayList<responsepartdata>()
    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parts_detail)
        toolbar = findViewById<View>(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        parts = findViewById(R.id.text)
        drawer = findViewById(R.id.my_drawer_layout)
        nav_view = findViewById(R.id.showparts)
        buttonaction = findViewById(R.id.buttonPanel)
        coverimage = findViewById(R.id.coverimage)
        partstitel = findViewById(R.id.partstitel)
        share = findViewById(R.id.share)
        comment = findViewById(R.id.comment)
        likecount = findViewById(R.id.likecount)
        commentcount = findViewById(R.id.commentcount)
        readcount = findViewById(R.id.readcount)
        likepart = findViewById(R.id.likepart)
        photoforparts = nav_view.getHeaderView(0).findViewById(R.id.partphotos)
        titelforparts = nav_view.getHeaderView(0).findViewById(R.id.titelforparts)
        val sharedPreferences = getSharedPreferences(
            AuthenticationActivity.MyPERF,
            Context.MODE_PRIVATE
        )
        val position = intent.getIntExtra("position", 0)
        findViewById<LinearLayout>(R.id.check).setOnClickListener {
            val int = Intent(this, check::class.java)
            int.putExtra("id", position)
            startActivity(int)
        }
        jwt = sharedPreferences.getString(AuthenticationActivity.defaultvalue, null)
        viewModel = ViewModelProvider(this).get(AllData::class.java)
        fetchData(position.toString())
        comment.setOnClickListener {
            val int = Intent(this, check::class.java)
            int.putExtra("id", position)
            startActivity(int)
        }
        share.setOnClickListener {
//            val browserIntent = Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse("https://www.youtube.com/watch?v=wuiR-GfaZVg")
//            )
//            //  browserIntent.setPackage("com.android.chrome") // Whatever browser you are using
//            startActivity(browserIntent)
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, story)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)

        }
        show = AnimationUtils.loadAnimation(this, R.anim.showanmi)
        hide = AnimationUtils.loadAnimation(this, R.anim.hideanmi)
        with(parts) {
            setOnClickListener {
                if (toolbar.visibility == View.VISIBLE) {
                    toolbar.animate().translationY(-100F).setDuration(400L)
                        .withEndAction(Runnable { toolbar.visibility = View.GONE }).start()
                    buttonaction.startAnimation(hide)
                    buttonaction.visibility = View.GONE
                } else if (toolbar.visibility == View.GONE) {
                    toolbar.visibility = View.VISIBLE
                    toolbar.animate().translationY(0F).setDuration(400L).start()
                    buttonaction.visibility = View.VISIBLE
                    buttonaction.startAnimation(show)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged", "RestrictedApi")
    private fun fetchData(id: String) {
        allParts = PartsViewModel(PartsDateRepository())
        allParts.episodesLiveData(jwt!!, id).observe(this, Observer {
            val data = it
            partstitel.text = data.partInfo.title
            parts.text =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(
                    (data.partInfo.content),
                    Html.FROM_HTML_MODE_COMPACT
                )
                else Html.fromHtml(data.partInfo.content)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
               story= Html.fromHtml((data.partInfo.content), Html.FROM_HTML_MODE_COMPACT).toString()
            else story= Html.fromHtml(data.partInfo.content).toString()

            likecount.setText(data.likeCount)
            commentcount.setText(data.commentsCount.toString())
            readcount.setText(data.readCount)
            Picasso.get().load("http://192.168.100.212:1337${data.partInfo.pathCoverUrl}")
                .into(coverimage)
            Picasso.get().load("http://192.168.100.212:1337${data.partInfo.pathCoverUrl}")
                .into(photoforparts)
            mainview = findViewById(R.id.mainview)
            likepart.setOnClickListener {
                likePart(id)
            }
            val menu: Menu = nav_view.getMenu()
            if (menu.size() == 0) {
                for (i in 1..data.contentMenu.parts.size)
                    menu.add(data.contentMenu.parts[i - 1].title)
            }
            menu.forEach {
                it.setOnMenuItemClickListener { p0: MenuItem? ->
                    Toast.makeText(applicationContext, "CLick $p0", Toast.LENGTH_SHORT)
                        .show()
                    p0!!.setCheckable(true)
                    for (i in 1..data.contentMenu.parts.size) {
                        when (p0.toString()) {
                            data.contentMenu.parts[i - 1].title -> {
                                fetchData(data.contentMenu.parts[i - 1].id.toString())
                            }
                        }
                    }

                    drawer.closeDrawer(Gravity.RIGHT)
                    true
                }
            }
        })
    }

    private fun likePart(id: String) {
        val call = RetrofitClient.JsonApi.likepart("Bearer $jwt", id)
        call.enqueue(object : Callback<likeresponce> {
            override fun onResponse(
                call: Call<likeresponce>,
                response: Response<likeresponce>
            ) {
                if (response.code() == 200) {
                    fetchData(id)
                }
            }

            override fun onFailure(call: Call<likeresponce>, t: Throwable) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.showparts, menu)
        // val item: MenuItem = menu.findItem(R.id.parts)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.parts) {
            if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                drawer.closeDrawer(Gravity.RIGHT)
            } else {
                drawer.openDrawer(Gravity.RIGHT)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
            super.onBackPressed()
    }
}