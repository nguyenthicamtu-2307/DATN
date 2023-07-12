package com.example.foryou.ViewModel.Adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.RescueTem.DataItemDonation

class ListDonationPost: RecyclerView.Adapter<ListDonationPost.MyViewHolder>() {
    private var onDonation : OnDonation? = null
    var list= mutableListOf<DataItemDonation>()
    fun replaceList(listAbc: MutableList<DataItemDonation>) {
        list = listAbc
        notifyDataSetChanged()
    }
    class MyViewHolder(view: View):RecyclerView.ViewHolder(view){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}
interface OnDonation {
    fun onItemClick(dataItem: DataItemDonation)
}