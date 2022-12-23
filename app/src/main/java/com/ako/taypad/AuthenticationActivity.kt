package com.ako.taypad

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.ako.taypad.Retrofit.RetrofitClient
import com.ako.taypad.model.loginuser
import com.ako.taypad.model.responseUser
import com.ako.taypad.model.signupuser
import retrofit2.*


class AuthenticationActivity : AppCompatActivity() {
    companion object {
        val MyPERF = "MyPerf"
        val defaultvalue = "default"
    }

    lateinit var tay: TextView
    lateinit var updown: Animation
    lateinit var progressDialog: ProgressDialog
    lateinit var sharedPreferences: SharedPreferences
    lateinit var resetform: RelativeLayout
    lateinit var forgetpass: TextView
    lateinit var resetcancel: Button
    lateinit var fade: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        supportActionBar?.hide()
        val gotologin = findViewById<TextView>(R.id.gotologin)
        val gotosignup = findViewById<TextView>(R.id.gotosignup)
        val loginform = findViewById<RelativeLayout>(R.id.loginform)
        val signupform = findViewById<RelativeLayout>(R.id.signupform)
        val name = findViewById<EditText>(R.id.username)
        val email = findViewById<EditText>(R.id.email)
        val passward = findViewById<EditText>(R.id.passward)
        val loginemail = findViewById<EditText>(R.id.loginemail)
        val loginpassward = findViewById<EditText>(R.id.loginpassward)
        val login = findViewById<Button>(R.id.loginbtn)
        val signin = findViewById<Button>(R.id.signup)
        resetform = findViewById(R.id.resetlayout)
        forgetpass = findViewById(R.id.loginforgetpass)
        resetcancel = findViewById(R.id.resetcancel)
        fade = AnimationUtils.loadAnimation(this, R.anim.fade)
        forgetpass.setOnClickListener {
            resetform.startAnimation(fade)
            resetform.visibility = View.VISIBLE
            loginform.visibility = View.GONE
        }
        resetcancel.setOnClickListener {
            resetform.visibility = View.GONE
            loginform.startAnimation(fade)
            loginform.visibility = View.VISIBLE
        }
        tay = findViewById<TextView>(R.id.tay)
        val logintay = findViewById<TextView>(R.id.logintay)
        updown = AnimationUtils.loadAnimation(this, R.anim.updown)
        tay.animation = updown
        sharedPreferences = getSharedPreferences(MyPERF, Context.MODE_PRIVATE)
        gotologin.setOnClickListener {
            signupform.visibility = View.GONE
            loginform.visibility = View.VISIBLE
            loginform.startAnimation(fade)
            logintay.startAnimation(updown)
        }
        gotosignup.setOnClickListener {
            signupform.visibility = View.VISIBLE
            signupform.startAnimation(fade)
            tay.startAnimation(updown)
            loginform.visibility = View.GONE
        }
        signin.setOnClickListener {
            val username = name.text.toString()
            val email = email.text.toString()
            val password = passward.text.toString()
            if (passward.length() >= 8) {
                val signupInfo = signupuser(username, email, password)
                progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Loading")
                progressDialog.setMessage("Please wait")
                progressDialog.show()
                progressDialog.setCancelable(false)
                Signup(signupInfo)
            } else if (passward.length() < 8) {
                Toast.makeText(
                    applicationContext,
                    "Password must be at least 8 character",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Enter a valid email address",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        login.setOnClickListener {
            val username = loginemail.text.toString()
            val password = loginpassward.text.toString()
            Log.d("Whynot", "$username and $email and $password")
            progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Loading")
            progressDialog.setMessage("Please wait")
            progressDialog.show()
            progressDialog.setCancelable(false)
            val logininfo = loginuser(username, password)
            Login(logininfo)
        }
        passward.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (p0!!.length < 8) {

                    passward.setError("Password must be at least 8 character", null)
                } else {
                    passward.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.length < 8) {
                    passward.setError("Password must be at least 8 character", null)
                } else {
                    passward.error = null
                }
            }

        })
    }

    fun Signup(info: signupuser) {
        val postapi = RetrofitClient.JsonApi.signup(info)
        postapi.enqueue(object : Callback<responseUser> {
            override fun onResponse(call: Call<responseUser>, response: Response<responseUser>) {
                val edit = sharedPreferences.edit()
                val getData = response.body()
                if (response.code() == 200 && getData != null) {
                    edit.putString(defaultvalue, getData.jwt)
                    edit.apply()
                    progressDialog.hide()
                    startActivity(Intent(this@AuthenticationActivity, MainActivity::class.java))
                    finish()
                } else if (response.code() == 400) {
                    progressDialog.hide()
                    Toast.makeText(
                        applicationContext, "Email or Username are already taken.",
                        Toast.LENGTH_LONG
                    ).show()
                }else{
                    progressDialog.hide()
                    Toast.makeText(
                        applicationContext, "Unable to connect to a server",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<responseUser>, t: Throwable) {
                progressDialog.hide()
                Toast.makeText(
                    applicationContext, "Unable to connect to a server",
                    Toast.LENGTH_LONG
                ).show()

            }

        })
    }

    fun Login(info: loginuser) {
        val postapi = RetrofitClient.JsonApi.login(info)
        postapi.enqueue(object : Callback<responseUser> {
            override fun onResponse(call: Call<responseUser>, response: Response<responseUser>) {
                val edit = sharedPreferences.edit()
                val getdata = response.body()
                Log.d("serverconnectiom",response.code().toString())
                if (response.code() == 200 && getdata != null) {
                    edit.putString(defaultvalue, getdata.jwt)
                    edit.apply()
                    progressDialog.hide()
                    startActivity(Intent(this@AuthenticationActivity, MainActivity::class.java))
                    finish()
                } else
                    if (response.code() == 400) {
                        progressDialog.hide()
                        Toast.makeText(
                            applicationContext,
                            "Invalid identifier or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        progressDialog.hide()
                        Toast.makeText(
                            applicationContext,
                            "Can't connect to a server",
                            Toast.LENGTH_SHORT).show()
                    }
            }
            override fun onFailure(call: Call<responseUser>, t: Throwable) {
                progressDialog.hide()
                Toast.makeText(
                    applicationContext,
                    "Unable to connect to a server",
                    Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onStart() {
        super.onStart()
        sharedPreferences = getSharedPreferences(MyPERF, Context.MODE_PRIVATE)
        val themedata = getSharedPreferences("Checkthems", Context.MODE_PRIVATE)
        val jwt = sharedPreferences.getString(AuthenticationActivity.defaultvalue, null)
        if (jwt != null) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
        val check = themedata.getInt("default", 0)
        if (check == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

}