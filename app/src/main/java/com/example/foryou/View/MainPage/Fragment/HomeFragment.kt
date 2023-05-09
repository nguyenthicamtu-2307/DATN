package com.example.foryou.View.Donation.MainPage.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.Event.DataItem
import com.example.foryou.Model.Event.Event
import com.example.foryou.Model.ListEvent
import com.example.foryou.Model.Retrofit.RetrofitIntance
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.ViewModel.Adapter.HomeAdapter
import com.example.foryou.ViewModel.HomeViewModle
import com.example.foryou.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: HomeAdapter
    private lateinit var apiService: getClient
    private var dataList = mutableListOf<ListEvent>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

       addListEVent()
        val sharedPref = context?.getSharedPreferences("MyRef", Context.MODE_PRIVATE)
        val myString = sharedPref?.getString("token", "")
        Log.d("t",myString.toString())

        return binding.root
    }

    fun addListEVent()
    {
        recyclerView = binding.rcvListEvent
        apiService = RetrofitIntance.getRetroInstance().create(getClient::class.java)
        apiService.getListEvent().enqueue(object : Callback<Event> {
            override fun onResponse(call: Call<Event>, response: Response<Event>) {
                if (response.isSuccessful){
                    val events = response.body()
                    val adapter = events?.let { HomeAdapter() }
                    recyclerView.adapter =adapter
                    recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

                    events?.data?.toMutableList()?.let { adapter?.replaceData(it) }
                }
                else {
                    Log.e("API", "Lỗi khi lấy dữ liệu: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Event>, t: Throwable) {
                Log.e("API", "Lỗi khi lấy dữ liệu", t)
            }

        })

    }

//fun addListEVent(){
//    viewModel = ViewModelProvider(this).get(HomeViewModle::class.java)
//    viewModel.getUser().observe(viewLifecycleOwner, Observer<List<DataItem>> {
//        if (it == null){
//            Toast.makeText(requireContext(),"no result ....",Toast.LENGTH_SHORT).show()
//
//        }else{
//            listAdapter.userList= it as MutableList<DataItem>
//            listAdapter.notifyDataSetChanged()
//        }
//    })
//    viewModel.addListEVent()
//}
}