package com.example.foryou.ViewModel.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.RescueTem.DataItemRelief
import com.example.foryou.Model.Soponsor.DataItem
import com.example.foryou.R
import java.text.SimpleDateFormat
import java.util.*

class ListDonateAdapter:RecyclerView.Adapter<ListDonateAdapter.MyViewHolder> (){

    private var OnDonations : OnDonates? = null
    fun replaceDatDonate(listAbc: MutableList<DataItem>) {
        dataList = listAbc
        notifyDataSetChanged()
    }
    private var dataList = mutableListOf<DataItem>()
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var  title = view.findViewById<TextView>(R.id.txtTitlePost)
        var  location=view.findViewById<TextView>(R.id.txtLocationPost)
        var time = view.findViewById<TextView>(R.id.txtTimePost)
        fun bind(dataItem: DataItem){
            title.text = dataItem.rescueTeam.name
            location.text = dataItem.status
           var timeLine= dataItem.deadline
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = SimpleDateFormat("dd/MM/yyyy")
            try {
                // Chuyển đổi chuỗi thành đối tượng Date
                val date: Date = inputFormat.parse(timeLine)

                val formattedDate = outputFormat.format(date)
                time.text = formattedDate.toString()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_post_aid,parent,false)
        return ListDonateAdapter.MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item = dataList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            OnDonations?.onItemClick(item)
        }

    }

    override fun getItemCount(): Int {
       return dataList.size
    }
    fun addClickDonate(clickListener: OnDonates){
        OnDonations = clickListener
    }
}
interface OnDonates {
    fun onItemClick(dataItem: DataItem)
}