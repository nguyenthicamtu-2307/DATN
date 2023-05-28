package com.example.foryou.View.Donation.MainPage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foryou.R

import com.example.foryou.View.Donation.MainPage.Fragment.*
import com.example.foryou.View.MainPage.Fragment.ActivityAid
import com.example.foryou.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navControler: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClick()

    }

    fun setNav() {

    }

    fun setOnClick() {
        val homeFragment = HomeFragment()
        //rescueTeam
        val manager = ManagerAidFragment()
        val planAid = PlanAidFragment()
        //local_officer
        val managerAid = ManagerFragment()
        val activityAid = ActivityAid()
        //sponor
        val donationList = ListDonationFragment()
        val historyDonation = HistoryDonationFragment()
        val person = PersonFragment()

        makeCurrentFragment(homeFragment)

        val bottomView = binding.nav
        val intent = intent
        val data_: String? = intent.getStringExtra("userType")
        val token_:String?= intent.getStringExtra("token")
        Log.d("usertype", data_.toString())
        Log.d("token", token_.toString())
        val sharedPref = getSharedPreferences("MyRef", Context.MODE_PRIVATE)
        val myString = sharedPref?.getString("token", "")
        Log.d("tok",myString.toString())
        binding.nav.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.navHome -> makeCurrentFragment(homeFragment)
                R.id.navManager -> {
                    if (data_ == "rescue_team") {
                        makeCurrentFragment(planAid)
                    } else {
                        if (data_ == "local_officer") {
                            makeCurrentFragment(managerAid)
                        } else {
                            if (data_ == "sponsor")
                                makeCurrentFragment(donationList)
                        }
                    }
                }
                R.id.navHistory -> {
                    if (data_ == "rescue_team") {
                        makeCurrentFragment(manager)
                    } else {
                        if (data_ == "local_officer") {
                            makeCurrentFragment(activityAid)
                        } else {
                            if (data_ == "sponsor")
                                makeCurrentFragment(historyDonation)
                        }
                    }
                }
                R.id.navPerson -> makeCurrentFragment(person)
            }
            true
        }
    }

    fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main, fragment)
            commit()
        }
    }
}