package com.example.foryou.ViewModel.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.Event.DataItem
import com.example.foryou.Model.ListEvent
import com.example.foryou.Model.ListItem
import com.example.foryou.Model.RescueTem.DataItemDonation
import com.example.foryou.R
import java.text.SimpleDateFormat
import java.util.*

class PostDonationAdapter(): RecyclerView.Adapter<PostDonationAdapter.MyViewHolder>() {
    private var onDonation : OnDonationClick? = null

    var list= mutableListOf<DataItemDonation>()
    fun replaceList(listAbc: MutableList<DataItemDonation>) {
        list = listAbc
        notifyDataSetChanged()
    }
    class MyViewHolder(view: View):RecyclerView.ViewHolder(view){

        var   image = view.findViewById<ImageView>(R.id.imagPost)
        var  title = view.findViewById<TextView>(R.id.txtTitlePost)
        var  location=view.findViewById<TextView>(R.id.txtLocationPost)
        var time = view.findViewById<TextView>(R.id.txtTimePost)
        var money = view.findViewById<TextView>(R.id.txtMoneyPost)
        var seeDetail = view.findViewById<TextView>(R.id.txtSee)
        fun bind(dataItem: DataItemDonation){
            title.text = dataItem.eventName
            location.text=dataItem.status
           var deadline = dataItem.deadline
            money.text= dataItem.moneyNeed.toInt().toString()
            seeDetail.text = dataItem.rescueTeam.name
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = SimpleDateFormat("dd/MM/yyyy")
            try {
                // Chuyển đổi chuỗi thành đối tượng Date
                val date: Date = inputFormat.parse(deadline)

                val formattedDate = outputFormat.format(date)
                time.text = formattedDate.toString()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_post_aid,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var data= list[position]
        holder.bind(data)
        holder.itemView.setOnClickListener {
            onDonation?.onItemClick(data)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun addItemDonationClick(clickListener : OnDonationClick){
        onDonation = clickListener
    }

}
interface OnDonationClick {
    fun onItemClick(dataItem: DataItemDonation)
}