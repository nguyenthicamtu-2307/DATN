package com.example.foryou.View.Register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast

import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foryou.Model.ListItem
import com.example.foryou.R
import com.example.foryou.ViewModel.RegisterViewModel
import com.example.foryou.databinding.ActivityRegisterBinding
import kotlinx.coroutines.selects.select

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        getList()

        setOnLick()
        setContentView(binding.root)
    }

    fun getList() {
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, viewModel.options.value!!)
        binding.autoComplete.adapter = adapter
    }

    fun setOnLick() {


        binding.autoComplete.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
               viewModel.onSelected(position)
                when(position){
                    0->{
                        var transaction = supportFragmentManager.beginTransaction()
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    transaction.replace(R.id.container, Register_canbo(), "canbo")
                        .commitAllowingStateLoss()
                    }
                    1->{
                        var transaction = supportFragmentManager.beginTransaction()
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        transaction.replace(R.id.container, Register_doicuutro(), "doicuutro")
                            .commitAllowingStateLoss()
                    }
                    2->{
                        var transaction = supportFragmentManager.beginTransaction()
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        transaction.replace(R.id.container, Register_Quyengop(), "nguoiquyengop")
                            .commitAllowingStateLoss()
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                var transaction = supportFragmentManager.beginTransaction()
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                transaction.replace(R.id.container, Register_canbo(), "canbo")
                    .commitAllowingStateLoss()
            }
        }
    }
}