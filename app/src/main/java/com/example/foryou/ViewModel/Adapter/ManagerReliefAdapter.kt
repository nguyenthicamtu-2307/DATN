package com.example.foryou.ViewModel.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.LocalOfficer.DataAction
import com.example.foryou.R
import java.text.SimpleDateFormat
import java.util.*

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
            var times= dataItem.createdAt
            location.text = dataItem.histories?.get(3)?.type
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = SimpleDateFormat("dd/MM/yyyy")
            try {
                // Chuyển đổi chuỗi thành đối tượng Date
                val date: Date = inputFormat.parse(times)

                val formattedDate = outputFormat.format(date)
                time.text = formattedDate.toString()

            } catch (e: Exception) {
                e.printStackTrace()
            }
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