package com.example.foryou.View.Onboard.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foryou.View.MainPage.HomeActivity
import com.example.foryou.View.Register.RegisterActivity
import com.example.foryou.databinding.FragmentThirdBinding


class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentThirdBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(inflater)
        // Inflate the layout for this fragment
        binding.apply {
            binding.tvFinish.setOnClickListener {
                val intent = Intent(activity, RegisterActivity::class.java)
                startActivity(intent)
                onBoardingFinished()
            }
        }
        return binding.root
    }

    private fun onBoardingFinished(){
        val sharedPref= requireActivity().getSharedPreferences("onBoarding",Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }
}