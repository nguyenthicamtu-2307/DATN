package com.example.foryou.View.Donation.MainPage.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.foryou.R
import com.example.foryou.View.Doicuutro.ActivitySumaryImage
import com.example.foryou.View.Doicuutro.InforAidRescue
import com.example.foryou.View.Doicuutro.ListDonationRescue
import com.example.foryou.databinding.FragmentManagerAidBinding

class ManagerAidFragment : Fragment() {

    private lateinit var binding: FragmentManagerAidBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentManagerAidBinding.inflate(inflater)
        onCLick()
        // Inflate the layout for this fragment
        return binding.root

    }

    fun onCLick(){

        binding.lnInfor.setOnClickListener {
            val intent = Intent(requireContext(),InforAidRescue::class.java)
            startActivity(intent)
        }
        binding.lnPost.setOnClickListener {
            val intent = Intent(requireContext(),ListDonationRescue::class.java)
            startActivity(intent)
        }
        binding.lnList.setOnClickListener {
            Log.d("bac","test")
            val intent = Intent(requireContext(),ListDonationRescue::class.java)
            startActivity(intent)
        }
        binding.lnToTal.setOnClickListener {
            val intent = Intent(requireContext(),ActivitySumaryImage::class.java)
            startActivity(intent)
        }
    }
}