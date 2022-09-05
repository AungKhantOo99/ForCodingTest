package com.ako.taypad.ui.Profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.finishAffinity
import com.ako.taypad.AuthenticationActivity
import com.ako.taypad.R

@SuppressLint("UseSwitchCompatOrMaterialCode")
class ProfileFragment : Fragment() {
    lateinit var sharedtheme: SharedPreferences
    lateinit var switchthems:Switch
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = view.findViewById<TextView>(R.id.jwt)
        val logout = view.findViewById<TextView>(R.id.logout)
        val general=view.findViewById<TextView>(R.id.general)
        val account=view.findViewById<TextView>(R.id.privacy)
        val reading=view.findViewById<TextView>(R.id.reading)
        switchthems=view.findViewById<Switch>(R.id.switchtheme)
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
        text.setText(jwt)
        logout.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(view.context, AuthenticationActivity::class.java))
            activity?.finish()
        }
//        changethems.setOnClickListener {
//            val builder= AlertDialog.Builder(requireContext())
//            builder.setTitle("Change Themes")
//            val style= arrayOf("Light","Dark")
//            val  check = 0
//            builder.setSingleChoiceItems(style , check){dialog,which->
//                when (which){
//                    0->{
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                        dialog.dismiss()
//                    }
//                    1->{
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                        dialog.dismiss()
//                    }
//                }
//            }
//            val dialog=builder.create()
//            dialog.show()
//        }
        switchthems.setOnCheckedChangeListener({ _ ,isChecked->
          if (isChecked){
              val editor=sharedtheme.edit()
              editor.putInt("default",1)
              editor.apply()
              AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
          }else{
              val editor=sharedtheme.edit()
              editor.clear()
              editor.apply()
              AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
          }
        })
        checkTheme()
    }
    private fun checkTheme(){
        val check= sharedtheme.getInt("default",0)
        if(check==1){
            switchthems.isChecked=true
        }
    }
}