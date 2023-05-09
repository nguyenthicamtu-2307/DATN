package com.example.foryou.ViewModel.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.ListEvent
import com.example.foryou.Model.ListItem
import com.example.foryou.R

class PostDonationAdapter(var context: Context): RecyclerView.Adapter<PostDonationAdapter.MyViewHolder>() {
    var datalist = emptyList<ListItem>()

    internal fun setData(datalist:List<ListItem>){
        this.datalist = datalist
    }

    class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        lateinit var image : ImageView
        lateinit var title : TextView
        lateinit var location : TextView
        lateinit var time : TextView
        lateinit var money: TextView
        lateinit var seeDetail:TextView

        init{
            image = view.findViewById(R.id.imagPost)
            title = view.findViewById(R.id.txtTitlePost)
            location=view.findViewById(R.id.txtLocationPost)
            time = view.findViewById(R.id.txtTimePost)
            money = view.findViewById(R.id.txtMoneyPost)
            seeDetail = view.findViewById(R.id.txtSee)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_post_aid,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var data= datalist[position]

        holder.title.text= data.title
        holder.location.text = data.location
        holder.time.text =data.time
        holder.money.text = data.money
        holder.seeDetail.text= data.detail
        holder.image.setImageResource(data.image)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }


}