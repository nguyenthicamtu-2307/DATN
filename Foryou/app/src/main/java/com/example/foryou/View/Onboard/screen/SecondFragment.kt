package com.example.foryou.View.Onboard.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.foryou.R
import com.example.foryou.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater)
        var viewPager =activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.apply {
            binding.tvFragNext2.setOnClickListener {
                viewPager?.currentItem=2
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }



}