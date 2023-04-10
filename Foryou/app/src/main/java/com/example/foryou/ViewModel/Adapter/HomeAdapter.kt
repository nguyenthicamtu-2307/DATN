package com.example.foryou.ViewModel.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.ListEvent
import com.example.foryou.R

class HomeAdapter(var context: Context): RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    var datalist = emptyList<ListEvent>()

    internal fun setData(datalist:List<ListEvent>){
      this.datalist = datalist
    }

    class MyViewHolder(view:View):RecyclerView.ViewHolder(view){
        lateinit var image :ImageView
        lateinit var title : TextView
        lateinit var location :TextView
        lateinit var time :TextView

        init{
            image = view.findViewById(R.id.imgAvatar)
            title = view.findViewById(R.id.txtTitle)
            location=view.findViewById(R.id.txtLocation)
            time = view.findViewById(R.id.txtTime)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_event,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var data= datalist[position]

        holder.title.text= data.title
        holder.location.text = data.location
        holder.time.text =data.time
        holder.image.setImageResource(data.image)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}