package com.example.foryou.ViewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foryou.Model.Event.DataItem
import com.example.foryou.Model.Retrofit.RetrofitIntance
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.ViewModel.Adapter.HomeAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModle:ViewModel() {
    var listRecycle: MutableLiveData<List<DataItem>> = MutableLiveData()
    fun getUser(): MutableLiveData<List<DataItem>> {
        return listRecycle
    }
//    fun addListEVent()
//    {
//       var apiService = RetrofitIntance.getRetroInstance().create(getClient::class.java)
//        apiService.getListEvent().enqueue(object : Callback<List<DataItem>> {
//            override fun onResponse(call: Call<List<DataItem>>, response: Response<List<DataItem>>) {
//                if (response.isSuccessful) {
//                    listRecycle.postValue(response.body())
//                } else {
//                    Log.e("API", "Lỗi khi lấy dữ liệu: ${response.message()}")
////
//                }
//
//            }
//
//            override fun onFailure(call: Call<List<DataItem>>, t: Throwable) {
//                Log.e("API", "Lỗi khi lấy dữ liệu", t)
//            }
//
//        })
//
//    }
}