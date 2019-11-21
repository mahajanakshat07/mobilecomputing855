package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(User::class)],version = 1)
abstract  class AppDB : RoomDatabase() {

    abstract fun safe() : dao
}