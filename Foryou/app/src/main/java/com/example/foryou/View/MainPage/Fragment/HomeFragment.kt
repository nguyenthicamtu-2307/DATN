package com.example.foryou.View.MainPage.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.ListEvent
import com.example.foryou.R
import com.example.foryou.ViewModel.Adapter.HomeAdapter
import com.example.foryou.databinding.FragmentFirstBinding
import com.example.foryou.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: HomeAdapter

    private var dataList = mutableListOf<ListEvent>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

       addListEVent()
        addListPost()


        return binding.root
    }


    fun addListEVent(){
        recyclerView =binding.rcvListEvent
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        listAdapter = HomeAdapter(requireContext())
        recyclerView.adapter = listAdapter
        addData()
    }
    fun addListPost(){
        recyclerView =binding.rcvListPost
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        listAdapter = HomeAdapter(requireContext())
        recyclerView.adapter = listAdapter
        addData()
    }
    fun addData(){

        // Inflate the layout for this fragment
        dataList.add(ListEvent("Phố cổ hội an sau lũ","Quảng nam","20/03/2023",R.drawable.imagelist))
        dataList.add(ListEvent("Phố cổ hội an sau lũ","Quảng nam","20/03/2023",R.drawable.imagelist))
        dataList.add(ListEvent("Phố cổ hội an sau lũ","Quảng nam","20/03/2023",R.drawable.imagelist))
        dataList.add(ListEvent("Phố cổ hội an sau lũ","Quảng nam","20/03/2023",R.drawable.imagelist))
        dataList.add(ListEvent("Phố cổ hội an sau lũ","Quảng nam","20/03/2023",R.drawable.imagelist))

        listAdapter.setData(dataList)

    }

}