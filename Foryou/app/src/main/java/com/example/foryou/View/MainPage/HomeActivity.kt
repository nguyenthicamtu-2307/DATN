package com.example.foryou.View.MainPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.foryou.R
import com.example.foryou.View.MainPage.Fragment.HistoryFragment
import com.example.foryou.View.MainPage.Fragment.HomeFragment
import com.example.foryou.View.MainPage.Fragment.ManagerFragment
import com.example.foryou.View.MainPage.Fragment.PersonFragment
import com.example.foryou.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    private lateinit var navControler : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClick()

    }

    fun setOnClick(){
        val homeFragment = HomeFragment()
        val manager= ManagerFragment()
        val history = HistoryFragment()
        val person = PersonFragment()
        makeCurrentFragment(homeFragment)
        binding.nav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navHome -> makeCurrentFragment(homeFragment)
                R.id.navManager -> makeCurrentFragment(manager)
                R.id.navHistory -> makeCurrentFragment(history)
                R.id.navPerson -> makeCurrentFragment(person)
            }
            true
        }
    }
    fun makeCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main,fragment)
            commit()
        }
    }
}