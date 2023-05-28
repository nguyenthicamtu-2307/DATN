package com.example.foryou.ViewModel.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.Proof.ProofsItem
import com.example.foryou.Model.RescueTem.DataItemRelief
import com.example.foryou.R
import com.squareup.picasso.Picasso

// on below line we are creating
// a course rv adapter class.
class CourseRVAdapter(): RecyclerView.Adapter<CourseRVAdapter.MyViewHolder>() {
    private var OnReliefOnClick : OnRCVListen? = null
    private var dataList = mutableListOf<ProofsItem>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName = view.findViewById<ImageView>(R.id.idIVCourse)
        val eventType = view.findViewById<TextView>(R.id.idTVCourse)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.rcv_image_acivity_rescue, parent, false)
        return CourseRVAdapter.MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item = dataList[position]
        Picasso.get().load(item.imageUrl).into(holder.textViewName)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    fun addRCVList(clickListener: OnRCVListen){
        OnReliefOnClick = clickListener
    }
}
interface OnRCVListen {
    fun onItemClick(dataItem: ProofsItem)
}
