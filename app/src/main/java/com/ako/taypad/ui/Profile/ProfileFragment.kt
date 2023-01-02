package com.ako.taypad.ui.Profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import com.ako.taypad.*
import com.ako.taypad.Retrofit.RetrofitClient
import com.ako.taypad.model.profiledata
import retrofit2.Call
import retrofit2.Response

@SuppressLint("UseSwitchCompatOrMaterialCode")
class ProfileFragment : Fragment() {
    lateinit var sharedtheme: SharedPreferences
    lateinit var switchthems:Switch
    lateinit var name:TextView
    lateinit var changepass:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name=view.findViewById(R.id.name)
        val mystories=view.findViewById<TextView>(R.id.mystories)
        val logout = view.findViewById<TextView>(R.id.logout)
        val general=view.findViewById<TextView>(R.id.general)
        val account=view.findViewById<TextView>(R.id.privacy)
        val reading=view.findViewById<TextView>(R.id.reading)
        switchthems=view.findViewById<Switch>(R.id.switchtheme)
        changepass=view.findViewById(R.id.privacy)
        val writeStory=view.findViewById<RelativeLayout>(R.id.write_story)
        val blurimg=view.findViewById<ImageView>(R.id.blurimage)
        changepass.setOnClickListener {
            requireActivity().run {
                val int=Intent(this, toolbarcheck::class.java)
                startActivity(int)
                //  requireActivity().overridePendingTransition(R.anim.showanmi,R.anim.fade)
            }
        }
        mystories.setOnClickListener {
            requireActivity().run {
                val int=Intent(this, MyStoriesActivity::class.java)
                startActivity(int)
                //  requireActivity().overridePendingTransition(R.anim.showanmi,R.anim.fade)
            }
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            blurimg.setRenderEffect(
//                RenderEffect.createBlurEffect(
//                    30f, //radius X
//                    30f, //Radius Y
//                    Shader.TileMode.CLAMP // X=CLAMP,DECAL,MIRROR,REPEAT
//                ))
//        }
        writeStory.setOnClickListener {
            requireActivity().run {
                val int=Intent(this, WritingStory::class.java)
                startActivity(int)
                //  requireActivity().overridePendingTransition(R.anim.showanmi,R.anim.fade)
            }
        }
        val sharedPreferences = requireActivity().getSharedPreferences(
            AuthenticationActivity.MyPERF,
            Context.MODE_PRIVATE
        )
        general.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext()).create()
            val view = layoutInflater.inflate(R.layout.dialogview,null)
            builder.setView(view)
            val  ok = view.findViewById<TextView>(R.id.ok)
            val cancel=view.findViewById<TextView>(R.id.cancel)
            builder.setView(view)
            ok.setOnClickListener {
                builder.dismiss()
            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }
        sharedtheme=requireActivity().getSharedPreferences("Checkthems",Context.MODE_PRIVATE)
        val jwt = sharedPreferences.getString(AuthenticationActivity.defaultvalue, null)
        logout.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(view.context, AuthenticationActivity::class.java))
            activity?.finish()
        }
        switchthems.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val editor = sharedtheme.edit()
                editor.putInt("default", 1)
                editor.apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                val editor = sharedtheme.edit()
                editor.clear()
                editor.apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        checkTheme()
        jwt?.let { GetProfiledata(it) }
    }
    private fun GetProfiledata(data:String){
        val getdata=RetrofitClient.JsonApi.getprofiledata("Bearer $data")
        getdata.enqueue(object :retrofit2.Callback<profiledata> {
            override fun onResponse(call: Call<profiledata>, response: Response<profiledata>) {
                if(response.code()==200){
                    name.setText(response.body()!!.username)
                }
            }

            override fun onFailure(call: Call<profiledata>, t: Throwable) {

            }

        })
    }
    private fun checkTheme(){
        val check= sharedtheme.getInt("default",0)
        if(check==1){
            switchthems.isChecked=true
        }
    }
}