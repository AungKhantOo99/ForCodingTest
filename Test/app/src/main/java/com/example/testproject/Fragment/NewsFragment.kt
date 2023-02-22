package com.example.testproject.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testproject.Adapter.NewsAdapter
import com.example.testproject.R
import com.example.testproject.ViewModel.NewsViewModel
import com.example.testproject.model.Article
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import java.util.*
import kotlin.collections.ArrayList

class NewsFragment : Fragment() {
   private lateinit var viewmodel:NewsViewModel
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val allnews= view.findViewById<RecyclerView>(R.id.allnews)
        allnews.layoutManager= LinearLayoutManager(context)
        shimmerFrameLayout=view.findViewById(R.id.shimmerLayout)
        shimmerFrameLayout.startShimmer()
        val builder = Shimmer.AlphaHighlightBuilder()
        builder.setDirection(Shimmer.Direction.BOTTOM_TO_TOP)
        shimmerFrameLayout.setShimmer(builder.build())
        viewmodel = ViewModelProvider(this)[NewsViewModel::class.java]
        viewmodel.getnewsdata().observe(viewLifecycleOwner, Observer {
            shimmerFrameLayout.visibility=View.GONE
            shimmerFrameLayout.stopShimmer()
            Log.d("CheckNews",it.toString())
            allnews.adapter=NewsAdapter(requireContext(),it.articles as ArrayList<Article>)
        })

    }

}