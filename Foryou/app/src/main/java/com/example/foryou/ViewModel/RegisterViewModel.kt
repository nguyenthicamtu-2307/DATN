package com.example.foryou.ViewModel

import android.content.Context
import android.util.Log
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foryou.Model.ListItem
import com.example.foryou.R
import com.example.foryou.View.Register.Register_canbo
import kotlin.coroutines.coroutineContext

class RegisterViewModel : ViewModel() {



    val options = MutableLiveData<List<String>>()
    val dropdownModel: LiveData<List<String>>
        get() = options

    init {
        val list= listOf("Cán bộ", "Đội cứu trợ", "Người quyên góp")
        // Thiết lập danh sách các lựa chọn
        options.value = list

    }
    fun onSelected(position:Int){


//        Toast.makeText(applicationContext, "Item at position $position is selected", Toast.LENGTH_SHORT).show()
    }
}