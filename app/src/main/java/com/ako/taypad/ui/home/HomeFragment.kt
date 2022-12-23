package com.ako.taypad.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ako.taypad.Adapter.HomeAdapter
import com.ako.taypad.Adapter.PopularAdapter
import com.ako.taypad.AuthenticationActivity
import com.ako.taypad.R
import com.ako.taypad.ViewModel.AllData
import com.ako.taypad.WritingStory
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    companion object{
        lateinit var recycler:RecyclerView
        lateinit var layoutManager: LinearLayoutManager

    }
     lateinit var viewmodel : AllData
     lateinit var shimmerFrameLayout:ShimmerFrameLayout
     lateinit var refresh:SwipeRefreshLayout
     lateinit var homeAdapter: HomeAdapter
     lateinit var popularAdapter: PopularAdapter
    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View? {
    return inflater.inflate(R.layout.fragment_home, container, false)
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler=view.findViewById(R.id.firstshow)
        val popular=view.findViewById<RecyclerView>(R.id.polularlist)
        val writeStory=view.findViewById<RelativeLayout>(R.id.write_story)
        refresh=view.findViewById(R.id.refresh)
        val SharedPreferences = requireActivity().getSharedPreferences(AuthenticationActivity.MyPERF, Context.MODE_PRIVATE)
        val jwt = SharedPreferences.getString(AuthenticationActivity.defaultvalue, null).toString()

        layoutManager=LinearLayoutManager(context)
        recycler.layoutManager=layoutManager
        popular.layoutManager=LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        recycler.isNestedScrollingEnabled=false
        shimmerFrameLayout=view.findViewById(R.id.shimmerLayout)
        shimmerFrameLayout.startShimmer()
        val builder = Shimmer.AlphaHighlightBuilder()
        builder.setDirection(Shimmer.Direction.BOTTOM_TO_TOP)
        shimmerFrameLayout.setShimmer(builder.build())
        viewmodel = ViewModelProvider(this).get(AllData::class.java)
        viewmodel.getdata(jwt).observe(viewLifecycleOwner, Observer {
            shimmerFrameLayout.visibility=View.GONE
            shimmerFrameLayout.stopShimmer()
            homeAdapter=HomeAdapter(requireContext(),it)
            popularAdapter=PopularAdapter(requireContext(),it)
            recycler.adapter= homeAdapter
            popular.adapter=popularAdapter
           // recycler.scrollToPosition(3)
            refresh.setOnRefreshListener {
                refresh.isRefreshing = false
                homeAdapter.notifyDataSetChanged()
                popularAdapter.notifyDataSetChanged()
            }
        })
        val snapHelperforpopular: SnapHelper = PagerSnapHelper()
        snapHelperforpopular.attachToRecyclerView(popular)
        writeStory.setOnClickListener {
            requireActivity().run {
                val int= Intent(this, WritingStory::class.java)
                startActivity(int)
            }
        }
    }

}