package com.example.finalproject

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(QuizEntity::class)],version = 1)
abstract class QuizDB : RoomDatabase() {

    abstract fun print() :QuizDao
}