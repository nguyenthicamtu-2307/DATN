package com.example.foryou.View.Doicuutro

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.Proof.ProofRescue
import com.example.foryou.Model.Proof.ProofRespone
import com.example.foryou.Model.Proof.ProofsItem
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.ViewModel.Adapter.CourseRVAdapter
import com.example.foryou.ViewModel.Adapter.OnRCVListen
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


class ActivitySumaryImage : AppCompatActivity(), OnRCVListen {

    private var selectImageUri: Uri? = null
    private lateinit var binding: ActivityImageActivityRescueBinding

    // on below line we are creating variables
    // for our swipe to refresh layout,
    // recycler view, adapter and list.
    lateinit var courseRV: RecyclerView
    private lateinit var adapterView: CourseRVAdapter
    private val PICK_IMAGE_REQUEST = 1
    private val SELECT_IMAGE_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityImageActivityRescueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // on below line we are initializing
        // our views with their ids.


        // on below line we are notifying adapter
        // that data has been updated.
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
        val baseURL = "http://192.168.143.2:3000/relief-app/v1/"
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
                    val listProvince = mutableListOf<String>()
                    if (data != null) {
                        for (i in data.indices) {
                            data[i].imageUrl.let {
                                listProvince.add(
                                    it
                                )
                            }
                        }
                    }
                    adapterView = CourseRVAdapter()
                    courseRV.adapter = adapterView
                    courseRV.layoutManager = GridLayoutManager(this@ActivitySumaryImage, 2)


                } else {
                    Toast.makeText(this@ActivitySumaryImage, response.message(), Toast.LENGTH_SHORT)
                        .show()

                }
            }

            override fun onFailure(call: Call<ProofRescue>, t: Throwable) {
                Toast.makeText(this@ActivitySumaryImage, "${t}", Toast.LENGTH_SHORT).show()

            }

        })
    }

    override fun onItemClick(dataItem: ProofsItem) {
    }


//    private fun getImagePath(uri: Uri?): String? {
//        val projection = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor = contentResolver.query(uri!!, projection, null, null, null)
//        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//        cursor?.moveToFirst()
//        val imagePath = cursor?.getString(columnIndex!!)
//        cursor?.close()
//        return imagePath
//    }

    fun openGrary() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)

    }

    fun setOnClick() {

        binding.btnUpload.setOnClickListener {
            showSelectImageDialog()
        }
    }

    private fun showSelectImageDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_image_custom, null)
        val buttonSelectImage = dialogView.findViewById<Button>(R.id.buttonSelectImage)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Select Image")

        val dialog = dialogBuilder.create()

        buttonSelectImage.setOnClickListener {
            // Gọi Intent để chọn hình ảnh từ thiết bị
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data

            // Lấy đường dẫn thực của hình ảnh đã chọn từ Uri
            val imagePath: String? = selectedImageUri?.let { uri ->
                getImagePathFromUri(uri)
            }

            // Kiểm tra và gửi hình ảnh lên API
            if (imagePath != null) {
                uploadImageToApi(imagePath)
            }
        }
    }
    @SuppressLint("Range")
    private fun getImagePathFromUri(uri: Uri): String? {
        // Lấy đường dẫn thực của hình ảnh từ Uri
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val imagePath = it.getString(it.getColumnIndex(MediaStore.Images.Media.DATA))
                return imagePath
            }
        }
        return null
    }
    private fun uploadImageToApi(id: String) {
        val imagePath = File(selectImageUri?.path)
        val imageFile = RequestBody.create("image/*".toMediaTypeOrNull(), imagePath)
        val imagePart = MultipartBody.Part.createFormData("image", imagePath.toString(), imageFile)
        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://192.168.143.2:3000/relief-app/v1/"
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
        api.uploadImage(id,imagePart).enqueue(object : Callback<ProofRespone>{
            override fun onResponse(call: Call<ProofRespone>, response: Response<ProofRespone>) {
                if (response.isSuccessful){
                    Log.e("API", "Lỗi khi lấy dữ liệu: ${response.message()}")
                    Toast.makeText(this@ActivitySumaryImage,"Post successfull", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(this@ActivitySumaryImage,response.message(), Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<ProofRespone>, t: Throwable) {
                Toast.makeText(this@ActivitySumaryImage,"${t}", Toast.LENGTH_SHORT).show()

            }

        })

    }
}
