package com.ako.taypad.ui.writing

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.ako.taypad.R
import com.ako.taypad.WritingStory

class WritingFragment : Fragment() {
    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_writing, container, false)
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//       val sharedtheme=requireActivity().getSharedPreferences("Checkthems",Context.MODE_PRIVATE)
//        val check= sharedtheme.getInt("default",0)
//        Log.d("Color",check.toString())
//        if(check==1) richEditor.setEditorFontColor(Color.WHITE)
//        else richEditor.setEditorFontColor(R.color.black)
        val startwrite=view.findViewById<Button>(R.id.write)
        startwrite.setText("Start Writing")
        startwrite.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this,WritingStory::class.java))
              //  finish()
            }
        //    activity?.startActivity(Intent(activity,WritingStory::class.java))
        }

    }

}