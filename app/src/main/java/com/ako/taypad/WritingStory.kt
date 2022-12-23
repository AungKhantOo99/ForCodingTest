package com.ako.taypad

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.ako.taypad.Retrofit.RetrofitClient
import com.ako.taypad.model.responceImage.responceImagedata
import com.ako.taypad.model.skipaction.data
import com.ako.taypad.model.skipaction.responsedata
import com.ako.taypad.model.skipaction.skipdata
import com.ako.taypad.model.sendstorydata.Data
import com.ako.taypad.model.sendstorydata.getdata
import com.ako.taypad.model.sendstorydata.postdata
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
    lateinit var progressDialog: ProgressDialog
    lateinit var photourl: Uri
    lateinit var add: ImageButton
    lateinit var post: Button
    lateinit var titel: com.google.android.material.textfield.TextInputEditText
    lateinit var description: com.google.android.material.textfield.TextInputEditText
    lateinit var tag: com.google.android.material.textfield.TextInputEditText
    lateinit var sharedPreferences: SharedPreferences
    lateinit var jwt: String
    lateinit var addtags: EditText
    lateinit var tags: TextView
    lateinit var tagschip: com.google.android.material.chip.ChipGroup
    lateinit var spinner: Spinner
    lateinit var arr: Array<String>
    var storytags = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writing_story)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        add = findViewById(R.id.addimage)
        post = findViewById(R.id.post)
        titel = findViewById(R.id.titel)
        description = findViewById(R.id.deception)
        addtags = findViewById(R.id.addtagslayout)
        tagschip = findViewById(R.id.tagslist)
        addtags.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

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
            Context.MODE_PRIVATE
        )
        arr = arrayOf(
            "Fantasy", "Horror", "Fiction", "Mystery", "Thriller",
            "LGBT", "Romance", "Gothic", "Young Adult",
            "Magical", "Science", "Adventure"
        )
        spinner = findViewById(R.id.category)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, arr
        )
        spinner.adapter = adapter
        jwt = sharedPreferences.getString(AuthenticationActivity.defaultvalue, null).toString()
        add.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
        post.setOnClickListener {
            progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Loading")
            progressDialog.setMessage("Please wait")
            progressDialog.show()
            progressDialog.setCancelable(false)
            postImage()
        }
    }

    private fun addChipToGroup(tags: String) {
        storytags.add(tags)
        val chip = Chip(this)
        chip.text = tags
        chip.chipIcon = ContextCompat.getDrawable(this, R.drawable.homes)
        chip.isChipIconVisible = true
        chip.isCloseIconVisible = true
        chip.isClickable = true
        chip.isCheckable = false
        tagschip.addView(chip as View)
        chip.setOnCloseIconClickListener {
            storytags.remove(chip.text)
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

    private fun postImage() {
        val name = resover(photourl)
        val file = createTmpFileFromUri(applicationContext, photourl, name)
        val requestFile =
            file!!.asRequestBody(contentResolver.getType(photourl)?.toMediaTypeOrNull())
        //   val requestBody= file?.let { RequestBody.create("image/*".toMediaTypeOrNull(), it) }
        Log.d("Whybot", name)
        val token: String = "Bearer " + jwt
        val call = RetrofitClient.JsonApi.postfile(
            token, files =
            MultipartBody.Part.createFormData("files", name, requestFile)
        )
        call.enqueue(object : Callback<responceImagedata> {
            override fun onResponse(
                call: Call<responceImagedata>,
                response: Response<responceImagedata>
            ) {
                if (response.code() == 200) {
                    val posttitel = titel.text.toString()
                    val postdescription = description.text.toString()
                    val category=2.toString()
                    var url=""
                    response.body()!!.forEach {
                        url=it.url
                    }
                    val Jwt = "Bearer $jwt"
                    val data = postdata(Data(category,postdescription,posttitel,url))
                    postStory(Jwt, data)
                }
            }
            override fun onFailure(call: Call<responceImagedata>, t: Throwable) {
                Log.d("Whynot", t.message.toString())
            }
        })
    }

    private fun postStory(jwt:String, info:postdata){
        val call=RetrofitClient.JsonApi.poststories(jwt,info)
        call.enqueue(object :Callback<getdata>{
            override fun onResponse(call: Call<getdata>, response: Response<getdata>) {
                if(response.code()==200){
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                    val int = Intent(this@WritingStory, StoryPartsActivity::class.java)
                    int.putExtra("postdata", response.body())
                    startActivity(int)
                    progressDialog.hide()
                }
            }

            override fun onFailure(call: Call<getdata>, t: Throwable) {

            }

        })
    }

    private fun createTmpFileFromUri(context: Context, uri: Uri, fileName: String): File? {
        return try {
            val stream = context.contentResolver.openInputStream(uri)
            val file = File.createTempFile(fileName, "", context.cacheDir)
            FileUtils.copyInputStreamToFile(stream, file)
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @SuppressLint("Range")
    private fun resover(url: Uri): String {
        var name = ""
        val cusor = managedQuery(url, null, null, null, null)
        cusor.use {
            it.moveToFirst()
            name = cusor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        return name
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.next, menu)
        val item: MenuItem = menu.findItem(R.id.next)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.next) {

            val info = data("Untitled Document")
            val postinfo = skipdata(info)
            val call = RetrofitClient.JsonApi.skipaction("Bearer $jwt", postinfo)
            call.enqueue(object : Callback<responsedata> {
                override fun onResponse(
                    call: Call<responsedata>,
                    response: Response<responsedata>
                ) {
                    if (response.code() == 200) {
                        val int = Intent(this@WritingStory, StoryPartsActivity::class.java)
                        int.putExtra("description", description.text.toString())
                        int.putExtra("titel", titel.text.toString())
                        int.putExtra("category", spinner.selectedItemId.toString())
                        int.putStringArrayListExtra("tags", storytags)
                        int.putExtra("getdata", response.body())
                        startActivity(int)
                    }
                }

                override fun onFailure(call: Call<responsedata>, t: Throwable) {

                }

            })
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
