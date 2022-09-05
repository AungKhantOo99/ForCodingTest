package com.ako.taypad.ui.home

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.ako.taypad.Adapter.HomeAdapter
import com.ako.taypad.Adapter.LastestAdapter
import com.ako.taypad.Adapter.PopularAdapter
import com.ako.taypad.AuthenticationActivity
import com.ako.taypad.MainActivity
import com.ako.taypad.R
import com.ako.taypad.ViewModel.AllData
import com.ako.taypad.model.example.bindexample


class HomeFragment : Fragment() {
     lateinit var viewmodel : AllData
     lateinit var mlayoutManager: LinearLayoutManager
    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View? {
    return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler=view.findViewById<RecyclerView>(R.id.firstshow)
        val lastest=view.findViewById<RecyclerView>(R.id.lastest)
        val popular=view.findViewById<RecyclerView>(R.id.polularlist)
        val progressDialog= ProgressDialog(requireContext())
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Please wait")
        progressDialog.show()
        progressDialog.setCancelable(false)
        progressDialog.hide()
        val SharedPreferences = requireActivity().getSharedPreferences(AuthenticationActivity.MyPERF, Context.MODE_PRIVATE)
        val jwt = SharedPreferences.getString(AuthenticationActivity.defaultvalue, null).toString()
        mlayoutManager=LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        recycler.layoutManager=mlayoutManager
        lastest.layoutManager=LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        popular.layoutManager=LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)

        viewmodel = ViewModelProvider(this).get(AllData::class.java)
        viewmodel.getExample().observe(viewLifecycleOwner, Observer {
            recycler.adapter=HomeAdapter(requireContext(),it)
            lastest.adapter=LastestAdapter(requireContext(),it)
            popular.adapter=PopularAdapter(requireContext(),it)
        })
        val snapHelper: SnapHelper = PagerSnapHelper()
        val snapHelperforpopular: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recycler)
        snapHelperforpopular.attachToRecyclerView(popular)
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val centerView = snapHelper.findSnapView(mlayoutManager) as View
                    val pos: Int = mlayoutManager.getPosition(centerView)
                    Log.d("Snapped Item Position:", "" + pos)
                }
            }
        })
     //   recycler.smoothScrollToPosition(2)
      //  snapHelper.attachToRecyclerView(lastest)
        viewmodel = ViewModelProvider(this).get(AllData::class.java)
//        viewmodel.getdata(jwt).observe(viewLifecycleOwner,Observer{
//            val binddata=it
//            Log.d("WHynot",binddata.toString())
//            it.data.forEach {
//                view.findViewById<TextView>(R.id.titel).text=it.title
//                Picasso.get().load("http://192.168.100.5:1337"+it.coverUrl).into(view.findViewById<ImageView>(R.id.coverphoto))
//                view.findViewById<TextView>(R.id.createdate).text=it.createdAt
//                view.findViewById<TextView>(R.id.updatedate).text=it.updatedAt
//            }
//            Log.d("Checkitt",it.toString())
//        })
    }

}