package com.example.foryou.ViewModel

import android.content.Context
import android.util.Log
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foryou.Model.Contents.District
import com.example.foryou.Model.ListItem
import com.example.foryou.Model.Retrofit.RetrofitIntance
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.Model.UserModel.User
import com.example.foryou.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class RegisterViewModel : ViewModel() {

    var listRecycle: MutableLiveData<User?> = MutableLiveData()

    fun getUser(): MutableLiveData<User?> {
        return listRecycle
    }
    val options = MutableLiveData<List<String>>()
    val dropdownModel: LiveData<List<String>>
        get() = options
    init {
        val list= listOf("LOCAL_OFFICER", "RESCUE_TEAM", "SPONSOR")
        // Thiết lập danh sách các lựa chọn
        options.value = list

    }



}