package com.example.foryou.ViewModel.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.Event.DataItem
import com.example.foryou.Model.Event.DataItemResCue
import com.example.foryou.R
import java.text.SimpleDateFormat
import java.util.*

class RescueAdapter() :
    RecyclerView.Adapter<RescueAdapter.MyViewHolder>() {

    private var onHomeItemClickListener : OnRescueOnClick? = null

    private var dataList = mutableListOf<DataItemResCue>()

    fun replaceDataRescue(listAbc: MutableList<DataItemResCue>) {
        dataList = listAbc
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName = view.findViewById<TextView>(R.id.txtTitle)
        val eventType = view.findViewById<TextView>(R.id.txtLocation)
        val createat = view.findViewById<TextView>(R.id.txtTime)


        fun bind(dataItem: DataItemResCue) {
            textViewName.text = dataItem.name
           var create = dataItem.createdAt
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = SimpleDateFormat("dd/MM/yyyy")
            try {
                // Chuyển đổi chuỗi thành đối tượng Date
                val date: Date = inputFormat.parse(create)

                val formattedDate = outputFormat.format(date)
                createat.text = formattedDate.toString()

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_event, parent, false)
        return RescueAdapter.MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item = dataList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onHomeItemClickListener?.onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    fun addRescueItem(clickListener : OnRescueOnClick){
        onHomeItemClickListener = clickListener
    }



}
interface OnRescueOnClick {
    fun onItemClick(dataItem: DataItemResCue)
}


