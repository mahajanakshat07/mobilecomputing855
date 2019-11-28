package com.example.assignment3

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(Image::class)],version = 1)
abstract class ImageDb  : RoomDatabase(){

    abstract fun akshat() : Imagedao
}