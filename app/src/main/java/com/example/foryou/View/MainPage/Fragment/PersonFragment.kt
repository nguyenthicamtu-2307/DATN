package com.example.foryou.View.Donation.MainPage.Fragment

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.foryou.Model.Proof.RescueSubProofRequest
import com.example.foryou.Model.Proof.RescueSubProofRespone
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.View.Canbo.DetailSubscription
import com.example.foryou.View.Doicuutro.ActivitySumaryImage
import com.example.foryou.View.Login.LoginActivity
import com.example.foryou.View.Person.InformationRescueTeam
import com.example.foryou.databinding.FragmentFourBinding
import com.example.foryou.databinding.FragmentManagerAidBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PersonFragment : Fragment() {
    private lateinit var binding: FragmentFourBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFourBinding.inflate(inflater)
        // Inflate the layout for this fragment
        setOnClick()
        getInformation()
        return binding.root

    }

    fun getInformation() {
        val getUser = context?.getSharedPreferences("Myref", Context.MODE_PRIVATE)
        val accessToken = getUser?.getString("token", "")
        var emaail = getUser?.getString("email","")
        var fullName = getUser?.getString("fullName","")
        binding.txtFullName.text = fullName.toString()
        binding.txtEmail.text = emaail.toString()
    }

    fun setOnClick() {
        binding.lnTTCN.setOnClickListener {
            val intent = Intent(requireContext(), InformationRescueTeam::class.java)
            startActivity(intent)
        }
        binding.lnHistory.setOnClickListener {
            val intent = Intent(requireContext(), DetailSubscription::class.java)
            startActivity(intent)
        }
        binding.lnLogout.setOnClickListener {
            showDialog()
        }
    }
    private fun showDialog() {

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_custom)

        val textViewMessage = dialog.findViewById<TextView>(R.id.textViewMessage)
        val buttonYes = dialog.findViewById<Button>(R.id.buttonYes)
        val buttonNo = dialog.findViewById<Button>(R.id.buttonNo)

        textViewMessage.text = "Bạn chắc chắn muốn đăng xuất?"



        buttonYes.setOnClickListener {
            // Xử lý khi nút Yes được nhấn
           var intent = Intent(requireContext(),LoginActivity::class.java)
            startActivity(intent)

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