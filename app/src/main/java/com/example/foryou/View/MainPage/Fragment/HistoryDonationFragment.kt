package com.example.foryou.View.Donation.MainPage.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import com.example.foryou.R
import com.example.foryou.databinding.FragmentHistoryDonationBinding


class HistoryDonationFragment : Fragment() {
    private lateinit var binding: FragmentHistoryDonationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryDonationBinding.inflate(inflater)


        // Inflate the layout for this fragment
        return binding.root
    }



    }


