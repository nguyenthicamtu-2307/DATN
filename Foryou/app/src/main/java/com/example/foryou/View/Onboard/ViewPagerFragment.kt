package com.example.foryou.View.Onboard

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.foryou.View.Onboard.ViewPagerAdapter
import com.example.foryou.View.Onboard.screen.FirstFragment
import com.example.foryou.View.Onboard.screen.SecondFragment
import com.example.foryou.View.Onboard.screen.ThirdFragment
import com.example.foryou.databinding.FragmentViewPagerBinding


class ViewPagerFragment : Fragment() {
    private val COMPLETED_ONBOARDING_PREF_NAME = "on_boarding_pref"

    private lateinit var binding:FragmentViewPagerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentViewPagerBinding.inflate(inflater)
        val shared = activity?.getSharedPreferences("mySp", AppCompatActivity.MODE_PRIVATE)
        val value = shared?.getBoolean("firstTime",true);

        // Inflate the layout for this fragment
        var fragmentList = arrayListOf<Fragment>(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment()
        )

        var adapter= ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter=adapter
        return binding.root
    }


}