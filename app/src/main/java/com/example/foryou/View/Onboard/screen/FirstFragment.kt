package com.example.foryou.View.Onboard.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.foryou.R
import com.example.foryou.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private lateinit var binding :  FragmentFirstBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFirstBinding.inflate(inflater)
        // Inflate the layout for this fragment
        var viewpaer = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.apply {
            binding.tvNext.setOnClickListener {
                viewpaer?.currentItem=1
            }
        }
        return binding.root

    }


}