package com.example.foryou.ViewModel.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foryou.Model.Proof.ProofsItem
import com.example.foryou.Model.RescueTem.DataItemRelief
import com.example.foryou.R
import com.squareup.picasso.Picasso

// on below line we are creating
// a course rv adapter class.
class CourseRVAdapter(private var imageUrlList: List<String>): RecyclerView.Adapter<CourseRVAdapter.MyViewHolder>() {

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
        var item = imageUrlList[position]
        Glide.with(holder.itemView)
            .load(item)
            .into(holder.textViewName)

    }
    fun setProofs(proofs: List<ProofsItem>?) {
        imageUrlList = proofs?.map { it.imageUrl } ?: emptyList()
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return imageUrlList.size
    }

}

