package com.ako.taypad

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.ako.taypad.Retrofit.LoginRetrofit
import com.ako.taypad.model.signupuser
import com.ako.taypad.model.responseUser
import retrofit2.*

class AuthenticationActivity : AppCompatActivity() {
    companion object{
        val MyPERF="MyPerf"
        val defaultvalue="default"
    }
    lateinit var progressDialog : ProgressDialog
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        supportActionBar?.hide()
        val gotologin=findViewById<TextView>(R.id.gotologin)
        val gotosignup=findViewById<TextView>(R.id.gotosignup)
        val loginform=findViewById<RelativeLayout>(R.id.loginform)
        val signupform=findViewById<RelativeLayout>(R.id.signupform)
        val name=findViewById<EditText>(R.id.username)
        val email=findViewById<EditText>(R.id.email)
        val passward=findViewById<EditText>(R.id.passward)
        val loginemail=findViewById<EditText>(R.id.loginemail)
        val loginpassward=findViewById<EditText>(R.id.loginpassward)
        val login=findViewById<Button>(R.id.loginbtn)
        val signin=findViewById<Button>(R.id.signup)
        sharedPreferences= getSharedPreferences(MyPERF, Context.MODE_PRIVATE)
        gotologin.setOnClickListener {
            signupform.visibility=View.GONE
            loginform.visibility=View.VISIBLE
        }
        gotosignup.setOnClickListener {
            signupform.visibility=View.VISIBLE
            loginform.visibility=View.GONE
        }
        signin.setOnClickListener {
            val username=name.text.toString()
            val email= email.text.toString()
            val password=passward.text.toString()
            Log.d("Whynot","$username and $email and $password")
            progressDialog= ProgressDialog(this)
            progressDialog.setTitle("Loading")
            progressDialog.setMessage("Please wait")
            progressDialog.show()
            progressDialog.setCancelable(false)
            val signupinfo=signupuser(username,email,password)
        //    post(signupinfo)
            signupcheck(signupinfo)
        }
        passward.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 if(p0!!.length < 8){
                    passward.setError("Password must be at least 8 character",null)
                 }else{
                     passward.error=null
                 }
            }
            override fun afterTextChanged(p0: Editable?) {
                if(p0!!.length < 8){
                    passward.setError("Password must be at least 8 character",null)
                }else{
                    passward.error=null
                }
            }

        })
    }
    fun signupcheck(data:signupuser){
        if(data.email=="ako@gmail.com"){
            progressDialog.hide()
            startActivity(Intent(this@AuthenticationActivity,MainActivity::class.java))
            finish()
        }
    }
    fun post(info:signupuser){
        val postapi= LoginRetrofit.JsonApi.signup(info)
        postapi.enqueue(object:Callback<responseUser>{
            override fun onResponse(call: Call<responseUser>, response: Response<responseUser>) {
                val edit=sharedPreferences.edit()
                val getdata= response.body()
                if (getdata != null) {
                    edit.putString(defaultvalue, getdata.jwt)
                    edit.apply()
                }
                progressDialog.hide()
                startActivity(Intent(this@AuthenticationActivity,MainActivity::class.java))
                finish()
                    Log.d("Whynot",response.code().toString())
            }
            override fun onFailure(call: Call<responseUser>, t: Throwable) {
               Log.d("Whathappen",t.message.toString())
                Toast.makeText(applicationContext,t.message.toString(),
                    Toast.LENGTH_SHORT).show()
                Log.d("Whynot",t.message.toString())
            }

        })
    }
    override fun onStart() {
        super.onStart()
        sharedPreferences= getSharedPreferences(MyPERF, Context.MODE_PRIVATE)
        val themedata=getSharedPreferences("Checkthems",Context.MODE_PRIVATE)
//        val jwt=sharedPreferences.getString(AuthenticationActivity.defaultvalue,null)
//        if(jwt != null){
//            startActivity(Intent(applicationContext,MainActivity::class.java))
//        }
        val check=themedata.getInt("default",0)
        if(check==1){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}