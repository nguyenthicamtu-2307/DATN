package com.example.foryou.View.Donation

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.foryou.Model.Donation.ConFirmRespone
import com.example.foryou.Model.Donation.ConfirmFinish
import com.example.foryou.Model.Proof.*
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.View.Doicuutro.ActivitySumaryImage
import com.example.foryou.View.Donation.MainPage.HomeActivity
import com.example.foryou.databinding.ActivityInforDonation2Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class InforDonation2 : AppCompatActivity() {
    private val REQUEST_CODE_IMAGE = 1
    private lateinit var binding: ActivityInforDonation2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInforDonation2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setClick()

    }

    fun setClick() {
        binding.txtUpload.setOnClickListener {
            requestPermission()
        }
        binding.btnXacNhan.setOnClickListener {
            getIDDonation()
        }
        binding.btnComplete.setOnClickListener {
            getID()
        }
    }
    private fun showDialog(id:String) {

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_custom)

        val textViewMessage = dialog.findViewById<TextView>(R.id.textViewMessage)
        val buttonYes = dialog.findViewById<Button>(R.id.buttonYes)
        val buttonNo = dialog.findViewById<Button>(R.id.buttonNo)

        textViewMessage.text = "Are you sure?"
        buttonYes.text = "finish"
        var fromMobile :Boolean = true
        var requets = ConfirmFinish("markAsComplete",fromMobile)
        buttonYes.setOnClickListener {
            // Xử lý khi nút Yes được nhấn
            var loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
            val baseURL = "http://172.20.10.5:3000/relief-app/v1/"
            //
            val sharedPreferences =getSharedPreferences("Myref", Context.MODE_PRIVATE)
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
            api.confirmDonationFinish(id,requets).enqueue(object : Callback<ConFirmRespone>{
                override fun onResponse(
                    call: Call<ConFirmRespone>,
                    response: Response<ConFirmRespone>
                ) {
                    if (response.isSuccessful){
                        var dataPost = response.body()
                        Toast.makeText(this@InforDonation2,"Cảm ơn bạn đã quyên góp!!", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this@InforDonation2,HomeActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@InforDonation2,response.message(), Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<ConFirmRespone>, t: Throwable) {
                    Toast.makeText(this@InforDonation2,"${t}", Toast.LENGTH_SHORT).show()

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
    fun getID() {
        val sharedRes = getSharedPreferences("DonateID", Context.MODE_PRIVATE)
        val id = sharedRes?.getString("DonateID", "")
        Log.d("is", id.toString())
        showDialog(id.toString())
    }
    fun getIDDonation() {
        val sharedRes = getSharedPreferences("DonateID", Context.MODE_PRIVATE)
        val id = sharedRes?.getString("DonateID", "")
        Log.d("is", id.toString())
        uploadImage(id.toString())
    }

    fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        )
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_IMAGE
            )
        else launchImagePicker()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_IMAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                launchImagePicker()
            else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_IMAGE
                )

            }
        }
    }

    private fun launchImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.dataString
            val selectedImagePath =
                selectedImageUri?.let { getURLFromImagePath(context = baseContext, it) }
            val imageFile = File(selectedImagePath)
            if (imageFile.exists()) {
                binding.txtUrlImange.text =selectedImagePath?.substring(1)

            } else {
                Toast.makeText(this, "Không tìm thấy tập tin ảnh", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getURLFromImagePath(context: Context, imagePath: String): String? {
        val contentResolver: ContentResolver = context.contentResolver
        val uri: Uri = Uri.parse(imagePath)

        val projection = arrayOf(MediaStore.Images.Media.DATA)
        var cursor: Cursor? = null

        try {
            cursor = contentResolver.query(uri, projection, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor?.close()
        }

        return null
    }

    private fun uploadImage(id: String) {
        var url = binding.txtUrlImange.text
        var emptyList : List<DonationDetailImagesItem> = listOf(DonationDetailImagesItem(url.toString()))
        var linkHinhanh = binding.edtUrl.text.toString()
        var fromBoolean: Boolean = true
        var request = ProofRequest(url.toString(), linkHinhanh.toString(),emptyList, fromBoolean)
        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://172.20.10.5:3000/relief-app/v1/"
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
        api.postProof(id, request).enqueue(object : Callback<ProofRespone> {
            override fun onResponse(call: Call<ProofRespone>, response: Response<ProofRespone>) {
                if (response.isSuccessful) {
                    var data = response.body()
                    Toast.makeText(
                        this@InforDonation2,
                        "Upload Minh chứng thành công",
                        Toast.LENGTH_SHORT
                    ).show()
                    var intent = Intent(this@InforDonation2,DonateDetailSponsor::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@InforDonation2, response.message(), Toast.LENGTH_SHORT)
                        .show()

                }
            }

            override fun onFailure(call: Call<ProofRespone>, t: Throwable) {
                Toast.makeText(this@InforDonation2, "${t}", Toast.LENGTH_SHORT).show()

            }

        })

    }


}
