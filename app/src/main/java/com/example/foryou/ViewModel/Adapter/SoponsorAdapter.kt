package com.example.foryou.ViewModel.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.RescueTem.DataItemDonation
import com.example.foryou.Model.Soponsor.DataItemPost
import com.example.foryou.R
import java.text.SimpleDateFormat
import java.util.*

class SoponsorAdapter: RecyclerView.Adapter<SoponsorAdapter.MyViewHolder>() {
    private var onDonation : OnPostDonation? = null

    var list= mutableListOf<DataItemPost>()
    fun replacePost(listAbc: MutableList<DataItemPost>) {
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
        fun bind(dataItem: DataItemPost){
            title.text = dataItem.eventName
            location.text=dataItem.status
            var times = dataItem.deadline
            money.text= dataItem.moneyNeed.toInt().toString()
            seeDetail.text = dataItem.rescueTeam.name
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
        return SoponsorAdapter.MyViewHolder(view)
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
    fun addDonationPost(clickListener: OnPostDonation){
        onDonation = clickListener
    }
}
interface OnPostDonation {
    fun onItemClick(dataItem: DataItemPost)
}