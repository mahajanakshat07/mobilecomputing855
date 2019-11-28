package com.example.assignment3

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(Enteries::class)],version = 1)
abstract class EnteriesDB:RoomDatabase(){
    abstract fun safe() : EnteriesDao
}