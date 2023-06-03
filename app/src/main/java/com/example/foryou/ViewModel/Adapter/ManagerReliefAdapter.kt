package com.example.foryou.ViewModel.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.LocalOfficer.DataAction
import com.example.foryou.R

class ManagerReliefAdapter: RecyclerView.Adapter<ManagerReliefAdapter.MyViewHolder>() {
    private var dataList = mutableListOf<DataAction>()
    private var OnDonations : OnListRescue? = null
    fun replaceDatList(listAbc: MutableList<DataAction>) {
        dataList = listAbc
        notifyDataSetChanged()
    }
    class MyViewHolder(view:View):RecyclerView.ViewHolder(view)  {
        var  title = view.findViewById<TextView>(R.id.txtTitlePost)
        var  location=view.findViewById<TextView>(R.id.txtLocationPost)
        var time = view.findViewById<TextView>(R.id.txtTimePost)
        var money = view.findViewById<TextView>(R.id.txtMoneyPost)
        fun bind(dataItem: DataAction){
            title.text = dataItem.rescueTeamName
            money.text = dataItem.amountOfMoney.toString()
            time.text= dataItem.createdAt
            location.text = dataItem.histories?.get(3)?.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_post_aid,parent,false)
        return ManagerReliefAdapter.MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var item = dataList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            OnDonations?.onListClick(item)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    fun addClickList(clickListener: OnListRescue){
        OnDonations = clickListener
    }
}
interface OnListRescue {
    fun onListClick(dataItem:DataAction)
}