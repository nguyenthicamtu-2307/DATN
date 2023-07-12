package com.example.foryou.View.Doicuutro

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.LocalOfficer.ConfirmRescueActionRequest
import com.example.foryou.Model.LocalOfficer.ConfirmRescueActionRespone
import com.example.foryou.Model.Proof.*
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.ViewModel.Adapter.CourseRVAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


import com.example.foryou.View.Doicuutro.ManagerAId.UploadRequestBody
import com.example.foryou.databinding.ActivityImageActivityRescueBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class ActivitySumaryImage : AppCompatActivity() {
    private val REQUEST_CODE_IMAGE = 1

    private var selectImageUri: Uri? = null
    private lateinit var binding: ActivityImageActivityRescueBinding

    // on below line we are creating variables
    // for our swipe to refresh layout,
    // recycler view, adapter and list.
    lateinit var courseRV: RecyclerView
    private lateinit var adapterView: CourseRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityImageActivityRescueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClick()
        getId()

    }

    fun getId() {
        val sharedId = getSharedPreferences("MyReliefId", Context.MODE_PRIVATE)
        val idDetail = sharedId?.getString("ReliefId", "")
//          val data_: String? = intent.getStringExtra("id")
//
        Log.d("idShare", idDetail.toString())
        getImage(idDetail.toString())


    }

    fun getImage(id: String) {
        courseRV = findViewById(R.id.idRVCourses)
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
        api.getProof(id).enqueue(object : Callback<ProofRescue> {
            override fun onResponse(call: Call<ProofRescue>, response: Response<ProofRescue>) {

                if (response.isSuccessful) {
                    val data = response.body()?.data?.proofs
                    Log.d("imange",data.toString())
                    if (data != null){
                        adapterView = CourseRVAdapter(emptyList())
                        courseRV.adapter = adapterView
                        courseRV.layoutManager = LinearLayoutManager(this@ActivitySumaryImage)
                        adapterView.setProofs(data)

                    }else{
                        Toast.makeText(this@ActivitySumaryImage,"NUll",Toast.LENGTH_SHORT).show()
                    }
                }else {
                    Toast.makeText(
                        this@ActivitySumaryImage,
                        response.message(),
                        Toast.LENGTH_SHORT
                    )
                        .show()

                }
            }


            override fun onFailure(call: Call<ProofRescue>, t: Throwable) {
                Toast.makeText(this@ActivitySumaryImage, "${t}", Toast.LENGTH_SHORT).show()

            }

        })
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

    @SuppressLint("MissingInflatedId")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.dataString
            val selectedImagePath =
                selectedImageUri?.let { getURLFromImagePath(context = baseContext, it) }
            val imageFile = File(selectedImagePath)
            if (imageFile.exists()) {
                binding.txtUrlHinhAnh.text = selectedImagePath?.substring(1)


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

    fun getImageUrl(){
        val sharedId = getSharedPreferences("MyReliefId", Context.MODE_PRIVATE)
        val idDetail = sharedId?.getString("ReliefId", "")
//          val data_: String? = intent.getStringExtra("id")
//
        Log.d("idShare", idDetail.toString())
        uploadImageToApi(idDetail.toString())
    }
    private fun uploadImageToApi(id: String) {
        var url = binding.txtUrlHinhAnh.text
        var emptyList : List<DonationDetailImagesItem> = listOf(DonationDetailImagesItem(url.toString()))

        var fromBoolean: Boolean = true
        var request = ProofRequest(url.toString(), url.toString(),emptyList, fromBoolean)
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
        api.uploadImage(id,request ).enqueue(object : Callback<ProofRespone> {
            override fun onResponse(call: Call<ProofRespone>, response: Response<ProofRespone>) {
                if (response.isSuccessful) {
                    Log.e("API", "Lỗi khi lấy dữ liệu: ${response.message()}")
                    Toast.makeText(this@ActivitySumaryImage, "Post successfull", Toast.LENGTH_SHORT)
                        .show()
                    var intent = Intent(this@ActivitySumaryImage,ActivitySumaryImage::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@ActivitySumaryImage, response.message(), Toast.LENGTH_SHORT)
                        .show()

                }
            }

            override fun onFailure(call: Call<ProofRespone>, t: Throwable) {
                Toast.makeText(this@ActivitySumaryImage, "${t}", Toast.LENGTH_SHORT).show()

            }

        })

    }
    fun getProofID(){
        val sharedId = getSharedPreferences("MyReliefId", Context.MODE_PRIVATE)
        val idDetail = sharedId?.getString("ReliefId", "")
//          val data_: String? = intent.getStringExtra("id")
//
        Log.d("idShare", idDetail.toString())
        showDialog(idDetail.toString())
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
        var requets = RescueSubProofRequest("markAsDone",fromMobile)
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
            api.conFirmRescueSubscription(id,requets).enqueue(object : Callback<RescueSubProofRespone>{
                override fun onResponse(
                    call: Call<RescueSubProofRespone>,
                    response: Response<RescueSubProofRespone>
                ) {
                    if (response.isSuccessful){
                        var dataPost = response.body()
                        Toast.makeText(this@ActivitySumaryImage,"Post successfull", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this@ActivitySumaryImage,ActivitySumaryImage::class.java)
                        startActivity(intent)

                    }else{
                        Toast.makeText(this@ActivitySumaryImage,response.message(), Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<RescueSubProofRespone>, t: Throwable) {
                    Toast.makeText(this@ActivitySumaryImage,"${t}", Toast.LENGTH_SHORT).show()

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

    fun setOnClick() {

        binding.lnUploadDonation.setOnClickListener {
            requestPermission()
        }
        binding.btnUpload.setOnClickListener {
            getImageUrl()
        }

        binding.confirm.setOnClickListener {
            getProofID()
        }
    }
}
