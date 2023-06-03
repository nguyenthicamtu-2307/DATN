package com.example.foryou.View.Donation.MainPage.Fragment

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.LocalOfficer.*
import com.example.foryou.Model.RescueTem.FinishDonationPost
import com.example.foryou.Model.RescueTem.FinishDonationPostRespone
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.View.Canbo.ManagerRelief
import com.example.foryou.View.Doicuutro.DetailRelief
import com.example.foryou.ViewModel.Adapter.ManagerReliefAdapter
import com.example.foryou.ViewModel.Adapter.OnListRescue
import com.example.foryou.databinding.FragmentSeconBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ManagerFragment : Fragment() {
    private lateinit var binding:FragmentSeconBinding
   private lateinit var id:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSeconBinding.inflate(inflater)
        getInforRescueAction()
        getOnClick()
        return binding.root
    }
    fun getOnClick(){
        binding.btnConfirmFinish.setOnClickListener {
            showDialog()
        }
    }
    fun  getIdEventSubscription(){
        val sharedId = context?.getSharedPreferences("Myis", Context.MODE_PRIVATE)
        val idDetail = sharedId?.getString("idShare", "")
//          val data_: String? = intent.getStringExtra("id")
//
        Log.d("idShare",idDetail.toString())
    }
    fun getInforRescueAction(){
        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://192.168.1.4:3000/relief-app/v1/"
        //
        val sharedPreferences = context?.getSharedPreferences("Myref", Context.MODE_PRIVATE)
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
        api.getListAction().enqueue(object : Callback<ListRescueAction> {
            override fun onResponse(
                call: Call<ListRescueAction>,
                response: Response<ListRescueAction>
            ) {
                if (response.isSuccessful){
                    var data = response.body()
                    binding.txtLocation.text= data?.data?.amountOfMoney.toString()
                    binding.txtEventName.text = data?.data?.neccessariesList
                    binding.txtStartAt.text = data?.data?.rescueTeamName
                    binding.txtEndAt.text = data?.data?.householdsListUrl
                    binding.txtMoneyDonate.text = data?.data?.reliefPlan?.aidPackage?.totalValue.toString()
                    binding.txtReliefNeccessary.text = data?.data?.reliefPlan?.aidPackage?.neccessariesList
                    binding.txtReliefStart.text = data?.data?.reliefPlan?.startAt
                    binding.txtReliefEnd.text = data?.data?.reliefPlan?.endAt
                    id = data?.data?.id.toString()
                }
                else{
                    Toast.makeText(requireContext(),response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ListRescueAction>, t: Throwable) {
                Toast.makeText(requireContext(),"${t}", Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun showDialog() {

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_custom)

        val textViewMessage = dialog.findViewById<TextView>(R.id.textViewMessage)
        val buttonYes = dialog.findViewById<Button>(R.id.buttonYes)
        val buttonNo = dialog.findViewById<Button>(R.id.buttonNo)

        textViewMessage.text = "Are you sure?"
        buttonYes.text = "finish"
        var requets = ConfirmRescueActionRequest("markAsDone")
        buttonYes.setOnClickListener {
            // Xử lý khi nút Yes được nhấn
            var loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
            val baseURL = "http://192.168.1.4:3000/relief-app/v1/"
            //
            val sharedPreferences =context?.getSharedPreferences("Myref", Context.MODE_PRIVATE)
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
            api.conFirmRescueActionOfLocalOfficer(requets, id).enqueue(object : Callback<ConfirmRescueActionRespone>{
                override fun onResponse(
                    call: Call<ConfirmRescueActionRespone>,
                    response: Response<ConfirmRescueActionRespone>
                ) {
                    if (response.isSuccessful){
                        var dataPost = response.body()
                        Toast.makeText(requireContext(),"Post successfull", Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(requireContext(),response.message(), Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<ConfirmRescueActionRespone>, t: Throwable) {
                    Toast.makeText(requireContext(),"${t}", Toast.LENGTH_SHORT).show()

                }

            })
        }

        buttonNo.setOnClickListener {
            // Xử lý khi nút No được nhấn
            dialog.dismiss()
        }

        dialog.setOnDismissListener(DialogInterface.OnDismissListener {
            // Xử lý khi hộp thoại được đóng
        })

        dialog.show()
    }
}