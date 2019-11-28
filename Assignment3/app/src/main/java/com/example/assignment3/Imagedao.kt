package com.example.assignment3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Imagedao {
    @Insert
    fun put(image: Image)

    @Query("Select * from Image")
    fun display() : List<Image>
}