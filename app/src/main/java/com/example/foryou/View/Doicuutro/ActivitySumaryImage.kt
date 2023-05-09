package com.example.foryou.View.Doicuutro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.CourseRVModal
import com.example.foryou.R
import com.example.foryou.ViewModel.Adapter.CourseRVAdapter


class ActivitySumaryImage : AppCompatActivity() {

    // on below line we are creating variables
    // for our swipe to refresh layout,
    // recycler view, adapter and list.
    lateinit var courseRV: RecyclerView
    lateinit var courseRVAdapter: CourseRVAdapter
    lateinit var courseList: ArrayList<CourseRVModal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_activity_rescue)

        // on below line we are initializing
        // our views with their ids.
        courseRV = findViewById(R.id.idRVCourses)

        // on below line we are initializing our list
        courseList = ArrayList()

        // on below line we are creating a variable
        // for our grid layout manager and specifying
        // column count as 2
        val layoutManager = GridLayoutManager(this, 2)

        courseRV.layoutManager = layoutManager

        // on below line we are initializing our adapter
        courseRVAdapter = CourseRVAdapter(courseList, this)

        // on below line we are setting
        // adapter to our recycler view.
        courseRV.adapter = courseRVAdapter

        // on below line we are adding data to our list
        courseList.add(CourseRVModal("Android Development", R.drawable.imagelist))
        courseList.add(CourseRVModal("C++ Development", R.drawable.imagelist))
        courseList.add(CourseRVModal("Java Development", R.drawable.imagelist))
        courseList.add(CourseRVModal("Python Development", R.drawable.imagelist))
        courseList.add(CourseRVModal("JavaScript Development", R.drawable.imagelist))
        courseList.add(CourseRVModal("JavaScript Development", R.drawable.imagelist))
        courseList.add(CourseRVModal("JavaScript Development", R.drawable.imagelist))
        courseList.add(CourseRVModal("JavaScript Development", R.drawable.imagelist))


        // on below line we are notifying adapter
        // that data has been updated.
        courseRVAdapter.notifyDataSetChanged()

    }
}