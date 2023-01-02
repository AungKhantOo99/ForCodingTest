package com.ako.taypad.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ako.taypad.Adapter.CategoryAdapter
import com.ako.taypad.Adapter.SearchAdapter
import com.ako.taypad.R

class Searchragment : Fragment() {
    lateinit var adapter: SearchAdapter
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
        val recycler = view.findViewById<RecyclerView>(R.id.idLVProgrammingLanguages)
        val cateview=view.findViewById<RecyclerView>(R.id.categoryitems)
        cateview.layoutManager=GridLayoutManager(requireContext(),2)
        recycler.layoutManager=LinearLayoutManager(requireContext())
        val category= arrayOf("Horror","Action","Adventure","CheckList","Romance")
        cateview.adapter=CategoryAdapter(requireContext(),category.distinct())
        adapter = SearchAdapter(requireContext(), category.distinct() as ArrayList<String> /* = java.util.ArrayList<kotlin.String> */)
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
    }

}

