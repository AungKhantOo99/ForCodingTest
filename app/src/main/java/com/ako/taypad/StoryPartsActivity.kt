package com.ako.taypad

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.ako.taypad.Retrofit.RetrofitClient
import com.ako.taypad.model.responceImage.responceImagedata
import com.ako.taypad.model.sendstorydata.getdata
import com.ako.taypad.model.skipaction.responsedata
import com.ako.taypad.model.writeparts.publicparts.publicpartsdata
import com.ako.taypad.model.writeparts.savedriftparts.Data
import com.ako.taypad.model.writeparts.savedriftparts.Story
import com.ako.taypad.model.writeparts.savedriftparts.savedriftpartsdata
import com.ako.taypad.model.writeparts.responseparts.responsepartsdata
import com.google.android.material.chip.Chip
import jp.wasabeef.richeditor.RichEditor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.apache.commons.io.FileUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class StoryPartsActivity : AppCompatActivity() {
    lateinit var progressDialog: ProgressDialog
    lateinit var photourl: Uri
    lateinit var jwt: String
    lateinit var sharedPreferences: SharedPreferences
    lateinit var partcover: ImageButton
    lateinit var sharedtheme: SharedPreferences
    lateinit var richtest: RichEditor
    var gettitle: String? = null
    var getdescription: String? = null
    var getcategory: String? = null
    lateinit var storydata: responsedata
    lateinit var storydataforpublic: getdata
    lateinit var tagsforstory: ArrayList<String>
    lateinit var tagsgroup: com.google.android.material.chip.ChipGroup
    lateinit var partstitle: com.google.android.material.textfield.TextInputEditText
    var checktitle = 0
    var checkdescription = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_parts)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        partstitle = findViewById(R.id.parttitle)
        richtest = findViewById(R.id.rich)
        partcover = findViewById(R.id.partcover)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        sharedtheme = getSharedPreferences("Checkthems", Context.MODE_PRIVATE)
        partcover.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
//        gettitle = intent.getStringExtra("titel")
//        getdescription = intent.getStringExtra("description")
//        getcategory = intent.getStringExtra("category")
//        tagsforstory = intent.getStringArrayListExtra("tags") as ArrayList<String>
//        storydata = intent.getSerializableExtra("getdata") as responsedata
        storydataforpublic = intent.getSerializableExtra("postdata") as getdata
        sharedPreferences = getSharedPreferences(
            AuthenticationActivity.MyPERF,
            Context.MODE_PRIVATE
        )
        Log.d("storydataforpublic",storydataforpublic.title)
        jwt = sharedPreferences.getString(AuthenticationActivity.defaultvalue, null).toString()
        TextProcess()
    }


    private fun TextProcess() {
        richtest.setPlaceholder("Start Writing")
        richtest.focusEditor()
        findViewById<ImageButton>(R.id.action_undo).setOnClickListener {
            richtest.undo()
        }
        findViewById<ImageButton>(R.id.action_bold).setOnClickListener {
            richtest.setBold()
        }
        findViewById<ImageButton>(R.id.action_italic).setOnClickListener {
            richtest.setItalic()
        }
        findViewById<ImageButton>(R.id.action_underline).setOnClickListener {
            richtest.setUnderline()
        }
        findViewById<ImageButton>(R.id.action_align_center).setOnClickListener {
            richtest.setAlignCenter()
        }
        findViewById<ImageButton>(R.id.action_align_left).setOnClickListener {
            richtest.setAlignLeft()
        }
        findViewById<ImageButton>(R.id.action_align_right).setOnClickListener {
            richtest.setAlignRight()
        }
        val check = sharedtheme.getInt("default", 0)
        if (check == 1) {
            richtest.setEditorFontColor(Color.WHITE)
            richtest.setTextColor(Color.WHITE)
        } else {
            richtest.setEditorFontColor(Color.BLACK)
            richtest.setTextColor(Color.BLACK)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.publish, menu)
//        val item: MenuItem = menu.findItem(R.id.publish)
//        val item: MenuItem = menu.findItem(R.id.post)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.publish) {
//            val dialog = BottomSheetDialog(this)
//            val view = LayoutInflater.from(this).inflate(R.layout.buttonsheet_forpublic, null)
//            dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
//            dialog.behavior.skipCollapsed = true
//            dialog.setContentView(view)
//            dialog.show()
//            val title = view.findViewById<EditText>(R.id.storytitel)
//            title.setText(gettitle)
//            val description = view.findViewById<EditText>(R.id.storydescription)
//            description.setText(getdescription)
//            val category = view.findViewById<Spinner>(R.id.storycategory)
//            val addtagsvalue = view.findViewById<EditText>(R.id.addtagslayout)
//            tagsgroup = view.findViewById(R.id.storytagslist)
//            val publish = view.findViewById<TextView>(R.id.postpublic)
//            val arr = arrayOf(
//                "Fantasy", "Horror", "Fiction", "Mystery", "Thriller",
//                "LGBT", "Romance", "Gothic", "Young Adult",
//                "Magical", "Science", "Adventure"
//            )
//            val adapter = ArrayAdapter(
//                this,
//                android.R.layout.simple_list_item_1, arr
//            )
//            category.adapter = adapter
//            category.setSelection(getcategory!!.toInt())
//            tagsforstory.forEach {
//                addChipToGroup(it)
//            }
//            addtagsvalue.addTextChangedListener(object : TextWatcher {
//                override fun afterTextChanged(s: Editable?) {
//                }
//
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    addtagsvalue.setOnEditorActionListener { _, actionId, _ ->
//                        if (actionId == EditorInfo.IME_ACTION_DONE) {
//                            // Call your code here
//                            if (!s.isNullOrEmpty()) {
//                                if (s.length >= 1) {
//                                    val text = s.toString().replace(",", "")
//                                    addChipToGroup(text)
//                                    addtagsvalue.setText("")
//                                }
//                            }
//                            true
//                        }
//                        true
//                    }
//                }
//            })
        } else if (item.itemId == R.id.post) {
            progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Loading")
            progressDialog.setMessage("Please wait")
            progressDialog.show()
            progressDialog.setCancelable(false)
            postImage()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addChipToGroup(tags: String) {
        val chip = Chip(this)
        chip.text = tags
        chip.chipIcon = ContextCompat.getDrawable(this, R.drawable.homes)
        chip.isChipIconVisible = true
        chip.isCloseIconVisible = true
        chip.isClickable = true
        chip.isCheckable = false
        tagsgroup.addView(chip as View)
        chip.setOnCloseIconClickListener {
            tagsgroup.removeView(chip as View)
            Toast.makeText(this, "${chip.text} has been removed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            photourl = data.data!!
            partcover.setImageURI(photourl)
        }
    }

    private fun postImage() {
        val name = resover(photourl)
        val file = createTmpFileFromUri(applicationContext, photourl, name)
        val requestFile =
            file!!.asRequestBody(contentResolver.getType(photourl)?.toMediaTypeOrNull())
        val token: String = "Bearer " + jwt
        val call = RetrofitClient.JsonApi.postfile(
            token, files =
            MultipartBody.Part.createFormData("files", name, requestFile)
        )
        val data=com.ako.taypad.model.writeparts.publicparts.Story(
            storydataforpublic.coverUrl.toString(),
            storydataforpublic.description.toString(),
            storydataforpublic.id.toInt(),
            storydataforpublic.title.toString()
        )

        call.enqueue(object : Callback<responceImagedata> {
            override fun onResponse(
                call: Call<responceImagedata>,
                response: Response<responceImagedata>
            ) {
                if (response.code() == 200) {
                    var url = ""
                    response.body()!!.forEach {
                        url = it.url
                    }
                    val info = publicpartsdata(
                        com.ako.taypad.model.writeparts.publicparts.Data(
                            richtest.html.toString(),
                            url,
                            data,
                            partstitle.text.toString()
                        )
                    )
                    public(info)

                }
            }

            override fun onFailure(call: Call<responceImagedata>, t: Throwable) {
                Log.d("Whynot", t.message.toString())
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

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do You Want to save as draft")
        builder.setPositiveButton("OK") { dialog, which ->
            saveasdrift()
            finish()
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            startActivity(Intent(this,MainActivity::class.java))
        }
        builder.show()

    }

    private fun public(info: publicpartsdata) {
        val call = RetrofitClient.JsonApi.publicparts("Bearer $jwt", info)
        call.enqueue(object : Callback<responsepartsdata> {
            override fun onResponse(call: Call<responsepartsdata>, response: Response<responsepartsdata>
            ) {
                if(response.code()==200){
                    progressDialog.hide()
                }
            }

            override fun onFailure(call: Call<responsepartsdata>, t: Throwable) {

            }

        })

    }

    private fun saveasdrift() {
        val mainstory = Story(storydata.id, storydata.title)
        val maindata = Data(
            richtest.html.toString(), "",
            "draft", mainstory, partstitle.text.toString()
        )
        val alldata = savedriftpartsdata(maindata)
        val call = RetrofitClient.JsonApi.savesadrift("Bearer $jwt", alldata)
        call.enqueue(object : Callback<responsepartsdata> {
            override fun onResponse(
                call: Call<responsepartsdata>,
                response: Response<responsepartsdata>
            ) {
                if (response.code() == 200) {
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<responsepartsdata>, t: Throwable) {

            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}