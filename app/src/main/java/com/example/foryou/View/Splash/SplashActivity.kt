package com.example.foryou.View.Splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.foryou.R
import com.example.foryou.View.Login.LoginActivity
import com.example.foryou.View.Donation.MainPage.HomeActivity
import com.example.foryou.View.Onboard.ViewPagerFragment
import com.example.foryou.View.Onboard.screen.FirstFragment

class SplashActivity : AppCompatActivity() {
    private val TIME_OUT = 2000;

    private val COMPLETED_ONBOARDING_PREF_NAME = "on_boarding_pref"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler(Looper.getMainLooper()).postDelayed({
//
            val mSharedPreferences = getSharedPreferences("mySp", MODE_PRIVATE)

            val firstTime = mSharedPreferences.getBoolean("firstTime", true)
            if (firstTime) {
                val editor = mSharedPreferences.edit()
                editor.putBoolean("firstTime", false)
                editor.commit()
                val trans: FragmentTransaction
                trans = supportFragmentManager.beginTransaction()

                trans.add(R.id.splash, ViewPagerFragment()).commit()
                //

            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

            }


        }, 2000)
    }
}