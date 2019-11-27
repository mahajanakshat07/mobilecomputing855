package com.example.finalproject

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao

interface QuizDao {

    @Query("Select * from Quiiz")
    fun read() : Array<QuizEntity>

    @Insert
    fun insert(quizEntity: QuizEntity)

    @Query("DELETE from quiiz where Questions =:abc ")
    fun remove(abc : String )
}