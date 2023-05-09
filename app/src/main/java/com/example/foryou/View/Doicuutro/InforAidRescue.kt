package com.example.foryou.View.Doicuutro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.foryou.R
import com.example.foryou.View.Donation.MainPage.Fragment.ManagerAidFragment
import com.example.foryou.databinding.ActivityInforAidBinding

class InforAidRescue : AppCompatActivity() {
    private lateinit var binding: ActivityInforAidBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInforAidBinding.inflate(layoutInflater)

        setOnClick()
        setContentView(binding.root)


    }

    fun setOnClick() {
        binding.imngBack.setOnClickListener {
            var transaction = supportFragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.replace(R.id.containerManagerAid, ManagerAidFragment(), "back")
                .commitAllowingStateLoss()
        }
    }
}