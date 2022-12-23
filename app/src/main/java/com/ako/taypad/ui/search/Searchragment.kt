package com.ako.taypad.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.Adapter.CategoryAdapter
import com.ako.taypad.Adapter.SearchAdapter
import com.ako.taypad.R
import com.ako.taypad.ViewModel.AllData
import com.ako.taypad.model.example.bindexample

class Searchragment : Fragment() {
    lateinit var adapter: SearchAdapter
    lateinit var data: ArrayList<bindexample>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val search = view.findViewById<android.widget.SearchView>(R.id.search)
        val Book = ArrayList<bindexample>()
        val recycler = view.findViewById<RecyclerView>(R.id.idLVProgrammingLanguages)
        val cateview=view.findViewById<RecyclerView>(R.id.categoryitems)
        cateview.layoutManager=GridLayoutManager(requireContext(),2)
        recycler.layoutManager=LinearLayoutManager(requireContext())
        val viewmodel = ViewModelProvider(this).get(AllData::class.java)
        viewmodel.getExample().observe(viewLifecycleOwner, Observer {
            val tags=ArrayList<String>()
            it.forEach {
                tags.add(it.tags)
            }
            cateview.adapter=CategoryAdapter(requireContext(),tags.distinct())
            adapter = SearchAdapter(requireContext(), tags.distinct() as ArrayList<String> /* = java.util.ArrayList<kotlin.String> */)
            recycler.adapter = adapter
            search.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return true
                }
            })

        })
    }

}

