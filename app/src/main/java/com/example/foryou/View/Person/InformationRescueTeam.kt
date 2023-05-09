package com.example.foryou.View.Person

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.foryou.R
import com.example.foryou.databinding.ActivityHomeBinding
import com.example.foryou.databinding.FragmentInformationRescueTeamBinding


class InformationRescueTeam : AppCompatActivity() {
    private lateinit var binding:FragmentInformationRescueTeamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentInformationRescueTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}