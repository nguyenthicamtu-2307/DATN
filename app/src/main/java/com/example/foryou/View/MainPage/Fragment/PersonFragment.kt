package com.example.foryou.View.Donation.MainPage.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foryou.R
import com.example.foryou.View.Canbo.DetailSubscription
import com.example.foryou.View.Person.InformationRescueTeam
import com.example.foryou.databinding.FragmentFourBinding
import com.example.foryou.databinding.FragmentManagerAidBinding


class PersonFragment : Fragment() {
private lateinit var binding:FragmentFourBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFourBinding.inflate(inflater)
        // Inflate the layout for this fragment
        setOnClick()

        return binding.root

    }

 fun setOnClick(){
     binding.lnTTCN.setOnClickListener {
         val intent = Intent(requireContext(),InformationRescueTeam::class.java)
         startActivity(intent)
     }
     binding.lnHistory.setOnClickListener {
         val intent = Intent(requireContext(),DetailSubscription::class.java)
         startActivity(intent)
     }
 }
}