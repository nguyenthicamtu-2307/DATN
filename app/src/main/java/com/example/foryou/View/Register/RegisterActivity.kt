package com.example.foryou.View.Register

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

import androidx.lifecycle.ViewModelProvider
import com.example.foryou.Model.Contents.*
import com.example.foryou.Model.Retrofit.MyInterceptors

import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.Model.UserModel.User
import com.example.foryou.R
import com.example.foryou.View.Donation.MainPage.HomeActivity
import com.example.foryou.View.Login.LoginActivity
import com.example.foryou.ViewModel.RegisterViewModel
import com.example.foryou.databinding.ActivityRegisterBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var apiService: getClient
    private lateinit var selectSpinnerId :String
    private lateinit var provinceId: String
    private lateinit var districtId :String
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        getList()
        checkCondition()
        getProvinced()
//        getDistrict()

        setOnLick()

        setContentView(binding.root)
    }


    fun getList() {

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, viewModel.options.value!!)
        binding.autoComplete.adapter = adapter
    }

    fun checkCondition() {
        binding.checkbox.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked) {
                binding.btnRegister.setBackgroundResource(R.drawable.button_custom)
                binding.btnRegister.isEnabled = true
            } else {
                binding.btnRegister.setBackgroundColor(Color.GRAY)
                binding.btnRegister.isEnabled = false
            }
        }
    }

    fun setOnLick() {

        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            apply()
        }
        binding.btnRegister.setOnClickListener {
            registerUser()

        }
        binding.txtDangNhap.setOnClickListener {
            var intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }


    }

//

    fun getProvinced() {
        var provinceList: List<ProvincesItem> = emptyList()

        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://192.168.1.4:3000/relief-app/v1/"
        //
        val sharedPreferences = getSharedPreferences("Myref", Context.MODE_PRIVATE)
        val client = OkHttpClient.Builder()
            .addInterceptor(MyInterceptors(sharedPreferences))
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(getClient::class.java)
        val call = api.getProvinced()


        call.enqueue(object : Callback<ProvincesRespon> {
            override fun onResponse(
                call: Call<ProvincesRespon>,
                response: Response<ProvincesRespon>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data?.provinces

                    val listProvince = arrayListOf<String>()
                    if (data != null) {
                        for (i in data.indices) {
                            data[i].name.let {
                                listProvince.add(
                                    it
                                )
                            }
                        }
                    }
                    val adapter = ArrayAdapter(
                        this@RegisterActivity,
                        android.R.layout.simple_spinner_item,
                        listProvince
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spProviced.adapter = adapter
                    binding.spProviced.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {

                                val ProvincesItem =
                                    data?.get(binding.spProviced.selectedItemPosition)
                                provinceId = ProvincesItem?.id.toString()
                                loadDistrictsByProvinceId(provinceId as String)
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }

                        }


                } else {

                    Toast.makeText(this@RegisterActivity, "fail load", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProvincesRespon>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "fail ", Toast.LENGTH_SHORT).show()
                Log.e("test", t.toString())

            }

        })


    }


    private fun loadDistrictsByProvinceId(provinceId: String) {
        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://192.168.1.4:3000/relief-app/v1/"
        //
        val sharedPreferences = getSharedPreferences("Myref", Context.MODE_PRIVATE)
        val client = OkHttpClient.Builder()
            .addInterceptor(MyInterceptors(sharedPreferences))
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(getClient::class.java)
        api.getDistrictbyProvince(provinceId).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data?.districts
                    val listProvince = arrayListOf<String>()
                    if (data != null) {
                        for (i in data.indices) {
                            data[i].name.let {
                                listProvince.add(
                                    it
                                )
                            }
                        }
                    }

                    if (data != null) {
                        // Cập nhật Spinner với danh sách quận huyện
                        val districtNames = data.map { it.name }

                        val adapter = ArrayAdapter(
                            this@RegisterActivity,
                            android.R.layout.simple_spinner_item,
                            districtNames
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.snpDistrict.adapter = adapter

                        binding.snpDistrict.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {

                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    val wardItem =
                                        data?.get(binding.snpDistrict.selectedItemPosition)
                                    districtId = wardItem?.id.toString()
                                    getWards(districtId as String)
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                    TODO("Not yet implemented")
                                }
                            }

                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("abc", t.toString())
            }
        })
    }

    fun getWards(district: String) {
        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://192.168.1.4:3000/relief-app/v1/"
        //
        val sharedPreferences = getSharedPreferences("Myref", Context.MODE_PRIVATE)
        val client = OkHttpClient.Builder()
            .addInterceptor(MyInterceptors(sharedPreferences))
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(getClient::class.java)
        api.getWardbyDistrict(district).enqueue(object : Callback<WardsRespon> {
            override fun onResponse(call: Call<WardsRespon>, response: Response<WardsRespon>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data?.wards
                    val listProvince = arrayListOf<String>()
                    if (data != null) {
                        for (i in data.indices) {
                            data[i].name.let {
                                listProvince.add(
                                    it
                                )
                            }
                        }
                    }

                    if (data != null) {
                        // Cập nhật Spinner với danh sách quận huyện
                        val districtNames = data.map { it.name }

                        val adapter = ArrayAdapter(
                            this@RegisterActivity,
                            android.R.layout.simple_spinner_item,
                            districtNames
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.snpWard.adapter = adapter
                        binding.snpWard.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                val wardItem =
                                    data?.get(binding.snpWard.selectedItemPosition)
                                selectSpinnerId = wardItem?.id.toString()
                                getWards(selectSpinnerId as String)
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }

                        }

                    }
                }
            }

            override fun onFailure(call: Call<WardsRespon>, t: Throwable) {
                Log.e("abc", t.toString())
            }
        })

    }

    fun registerUser() {
        val usertype= binding.autoComplete.selectedItem.toString()
        val email = binding.tvEmailQg.text.toString()
        val passWord = binding.edtMatKhauQg.text.toString()
        val firstName = binding.edtFirstName.text.toString()
        val middleName = binding.edtMiddleName.text.toString()
        val lastName= binding.edtLastName.text.toString()
        val phone = binding.edtPhoneNumber.text.toString()
        var username = binding.edtUsername.text.toString()

        var user = User(usertype,email,passWord,firstName,middleName,lastName,phone,username,provinceId,districtId,selectSpinnerId)
        Log.d("user",user.toString())
        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://192.168.1.4:3000/relief-app/v1/"
        //
        val sharedPreferences = getSharedPreferences("Myref", Context.MODE_PRIVATE)
        val client = OkHttpClient.Builder()
            .addInterceptor(MyInterceptors(sharedPreferences))
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(getClient::class.java)
        val call = api.register(user)
        call.enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val intent = Intent(this@RegisterActivity , LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    // Xử lý lỗi
                    val errorMessage = response.errorBody()?.string()
                    Log.e("abc",errorMessage.toString())
                    Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Lỗi: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

}