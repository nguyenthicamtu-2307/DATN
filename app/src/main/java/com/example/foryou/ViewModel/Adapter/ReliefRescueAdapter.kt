package com.example.foryou.ViewModel.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.Event.DataItemResCue
import com.example.foryou.Model.RescueTem.DataItemRelief
import com.example.foryou.R
import com.example.foryou.View.Doicuutro.ListReliefResue

class ReliefRescueAdapter: RecyclerView.Adapter<ReliefRescueAdapter.MyViewHolder>() {
    private var OnReliefOnClick : OnReliefClick? = null

    private var dataList = mutableListOf<DataItemRelief>()
    fun replaceDatRelife(listAbc: MutableList<DataItemRelief>) {
        dataList = listAbc
        notifyDataSetChanged()
    }
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName = view.findViewById<TextView>(R.id.txtTitle)
        val eventType = view.findViewById<TextView>(R.id.txtLocation)
        val createat = view.findViewById<TextView>(R.id.txtTime)
        fun bind(dataItem: DataItemRelief){
            textViewName.text = dataItem.eventName
            eventType.text = dataItem.isApproved.toString()
            createat.text= dataItem.path
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_event, parent, false)
        return ReliefRescueAdapter.MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item = dataList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            OnReliefOnClick?.onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
     return dataList.size
    }

    fun addRelief(clickListener: OnReliefClick){
        OnReliefOnClick = clickListener
    }

}
interface OnReliefClick {
    fun onItemClick(dataItem: DataItemRelief)
}