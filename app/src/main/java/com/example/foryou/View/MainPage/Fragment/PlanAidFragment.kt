package com.example.foryou.View.Donation.MainPage.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.ListEvent
import com.example.foryou.Model.ListItem
import com.example.foryou.R
import com.example.foryou.ViewModel.Adapter.HomeAdapter
import com.example.foryou.ViewModel.Adapter.PostDonationAdapter
import com.example.foryou.databinding.FragmentPlanAidBinding


class PlanAidFragment : Fragment() {
    private lateinit var binding: FragmentPlanAidBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: PostDonationAdapter
    private var dataList = mutableListOf<ListItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanAidBinding.inflate(inflater)
        // Inflate the layout for this fragment
        postDonation()
        return binding.root
    }


    fun postDonation(){
        recyclerView =binding.rcvListPostAid
        recyclerView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        listAdapter = PostDonationAdapter(requireContext())
        recyclerView.adapter = listAdapter
        addData()
    }

    fun addData(){
        // Inflate the layout for this fragment
        dataList.add(ListItem(R.drawable.imagelist,"Phố cổ hội an sau lũ","Quảng nam","500000","23/07/2013","Xem chi tiết"))
        dataList.add(ListItem(R.drawable.imagelist,"Phố cổ hội an sau lũ","Quảng nam","500000","23/07/2013","Xem chi tiết"))
        dataList.add(ListItem(R.drawable.imagelist,"Phố cổ hội an sau lũ","Quảng nam","500000","23/07/2013","Xem chi tiết"))
        dataList.add(ListItem(R.drawable.imagelist,"Phố cổ hội an sau lũ","Quảng nam","500000","23/07/2013","Xem chi tiết"))
        dataList.add(ListItem(R.drawable.imagelist,"Phố cổ hội an sau lũ","Quảng nam","500000","23/07/2013","Xem chi tiết"))
        dataList.add(ListItem(R.drawable.imagelist,"Phố cổ hội an sau lũ","Quảng nam","500000","23/07/2013","Xem chi tiết"))
        dataList.add(ListItem(R.drawable.imagelist,"Phố cổ hội an sau lũ","Quảng nam","500000","23/07/2013","Xem chi tiết"))
        dataList.add(ListItem(R.drawable.imagelist,"Phố cổ hội an sau lũ","Quảng nam","500000","23/07/2013","Xem chi tiết"))
        dataList.add(ListItem(R.drawable.imagelist,"Phố cổ hội an sau lũ","Quảng nam","500000","23/07/2013","Xem chi tiết"))
        listAdapter.setData(dataList)
    }
}