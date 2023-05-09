package com.example.foryou.storage.RoomDb

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class DatabaseRoom: RoomDatabase() {

    companion object{

        var recipesDatabase:DatabaseRoom? = null

        @Synchronized
        fun getDatabase(context: Context): DatabaseRoom{
            if (recipesDatabase == null){
                recipesDatabase = Room.databaseBuilder(
                    context,
                    DatabaseRoom::class.java,
                    "foryou.db"
                ).build()
            }
            return recipesDatabase!!
        }
    }

    abstract fun dataDao():DatabaseDao
}