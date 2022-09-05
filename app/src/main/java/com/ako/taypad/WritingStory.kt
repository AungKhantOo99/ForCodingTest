package com.ako.taypad

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.ako.taypad.Retrofit.testfilter
import com.ako.taypad.model.PostCategory
import com.ako.taypad.model.ResponcePostData.ResponcePostData
import com.ako.taypad.responceImage.responceImagedata
import com.google.android.material.chip.Chip
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.apache.commons.io.FileUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class WritingStory : AppCompatActivity() {
    lateinit var photourl: Uri
    lateinit var add:ImageButton
    lateinit var post:Button
    lateinit var titel:com.google.android.material.textfield.TextInputEditText
    lateinit var description:com.google.android.material.textfield.TextInputEditText
    lateinit var tag:com.google.android.material.textfield.TextInputEditText
    lateinit var deceriptionlayout:com.google.android.material.textfield.TextInputLayout
    lateinit var autocomplete:androidx.appcompat.widget.AppCompatAutoCompleteTextView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var jwt: String
    lateinit var addtags:EditText
    lateinit var tags:TextView
    lateinit var tagschip:com.google.android.material.chip.ChipGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writing_story)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        val colorDrawable = ColorDrawable(Color.parseColor("#ffffff"))
//        supportActionBar?.setBackgroundDrawable(colorDrawable)
        add=findViewById(R.id.addimage)
        post=findViewById(R.id.post)
        titel=findViewById(R.id.titel)
      //  tag=findViewById(R.id.tag)
        description=findViewById(R.id.deception)
        addtags=findViewById(R.id.addtagslayout)
        tagschip=findViewById(R.id.tagslist)
        addtags.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if (text.isNotEmpty() && (text.last().toString() == ","))
                    addtags.setText("")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                addtags.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        // Call your code here
                        if (!s.isNullOrEmpty()) {
                            if (s.length >= 1) {
                                val text = s.toString().replace(",", "")
                                addChipToGroup(text)
                                addtags.setText("")
                            }
                        }
                        true
                    }
                    true
                }
            }
        })

        sharedPreferences = getSharedPreferences(
            AuthenticationActivity.MyPERF,
            Context.MODE_PRIVATE)
        val arr = arrayOf("Fantasy","Horror","Fiction","Mystery","Thriller",
                         "LGBT","Romance","Gothic","Young Adult",
                         "Magical","Science","Adventure")
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, arr)
        autocomplete=findViewById(R.id.category)
        autocomplete.setThreshold(1)
        autocomplete.setAdapter(adapter)
        jwt = sharedPreferences.getString(AuthenticationActivity.defaultvalue, null).toString()
        add.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
//        post.setOnClickListener {
////            val posttitel=titel.text.toStrin g()
//            val postdescription=description.text.toString()
////            val postdata=DataX(posttitel,postdescription)
////            val Jwt="Bearer "+jwt
////            val cate=PostCategory(postdata)
////            Post(Jwt,cate)
//         //   PostImage()
//            if(description.length() <8){
//               deceriptionlayout=findViewById(R.id.deceptionlayout)
//                deceriptionlayout.error="Enter more than 8"
//
//            }else deceriptionlayout.error=null
//        }

    }
    private fun addChipToGroup(tags: String) {
        val chip = Chip(this)
        chip.text = tags
        chip.chipIcon = ContextCompat.getDrawable(this, R.drawable.homes)
        chip.isChipIconVisible = true
        chip.isCloseIconVisible = true
        chip.isClickable = true
        chip.isCheckable = false
        tagschip.addView(chip as View)
        chip.setOnCloseIconClickListener {
            tagschip.removeView(chip as View)
            Toast.makeText(this, "${chip.text} has been removed", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            photourl = data.data!!
            add.setImageURI(photourl)
        }
    }
    private fun Post(jwt:String?,data:PostCategory){
        val call= jwt?.let { testfilter.JsonApi.postdata(it,data) }
        call!!.enqueue(object :Callback<ResponcePostData>{
            override fun onResponse(call: Call<ResponcePostData>, response: Response<ResponcePostData>) {
                Log.d("onlycheck",response.code().toString())
            }
            override fun onFailure(call: Call<ResponcePostData>, t: Throwable) {
               Log.d("Whynot",t.message.toString())
            }

        })
    }
    private fun PostImage(){
        val name=Resover(photourl)
        val file= createTmpFileFromUri(applicationContext,photourl,name)
            val requestFile = file!!.asRequestBody(contentResolver.getType(photourl)?.toMediaTypeOrNull())
         //   val requestBody= file?.let { RequestBody.create("image/*".toMediaTypeOrNull(), it) }
            Log.d("Whybot",name)
            val token: String = "Bearer "+jwt
            val call=testfilter.JsonApi.postfile(token,files=
            MultipartBody.Part.createFormData("files", name, requestFile))
            call.enqueue(object :Callback<responceImagedata>{
                override fun onResponse(call: Call<responceImagedata>, response: Response<responceImagedata>) {
                    Log.d("Whynot",response.code().toString())
                    val data=response.body()
                    data!!.forEach {
                        val url=it.url
                        Log.d("Whynot",url)
                    }
                }
                override fun onFailure(call: Call<responceImagedata>, t: Throwable) {
                    Log.d("Whynot",t.message.toString())
                }
            })
    }
    private fun createTmpFileFromUri(context: Context, uri: Uri, fileName: String): File? {
        return try {
            val stream = context.contentResolver.openInputStream(uri)
            val file = File.createTempFile(fileName, "", context.cacheDir)
            FileUtils.copyInputStreamToFile(stream,file)
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    @SuppressLint("Range")
    private fun Resover(url:Uri):String{
        var name=""
        val cusor=managedQuery(url,null,null,null,null)
        cusor.use {
            it.moveToFirst()
            name=cusor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        return name
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.next, menu)
        val item: MenuItem = menu.findItem(R.id.next)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.next){
            Toast.makeText(applicationContext,"CLick",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,StoryPartsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
