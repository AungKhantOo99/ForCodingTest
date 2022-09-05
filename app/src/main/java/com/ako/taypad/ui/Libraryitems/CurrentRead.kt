package com.ako.taypad.ui.Libraryitems

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
import com.ako.taypad.Adapter.SearchResultAdapter
import com.ako.taypad.R
import com.ako.taypad.ViewModel.AllData
import com.ako.taypad.model.example.bindexample

class CurrentRead : Fragment() {
    companion object{
        val fav=ArrayList<String>()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_read, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentRead=view.findViewById<RecyclerView>(R.id.currentread)
        currentRead.layoutManager=LinearLayoutManager(requireContext())
        val viewModel=ViewModelProvider(this).get(AllData::class.java)
        viewModel.getExample().observe(viewLifecycleOwner, Observer {
            val check=ArrayList<String>()
            val filter=ArrayList<bindexample>()
            it.forEach {
                val binddate=it
                fav.distinct().forEach {
                    if(it.equals(binddate.titel)){
                        filter.add(binddate) }
                }
            }
            currentRead.adapter=SearchResultAdapter(requireContext(),filter)
        })
    }
}