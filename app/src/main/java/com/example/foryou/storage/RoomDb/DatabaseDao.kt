package com.example.foryou.storage.RoomDb

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.foryou.Model.Contents.Contents

interface DatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContent(contents: Contents?)
}